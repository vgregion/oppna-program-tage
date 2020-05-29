package se.goteborg.retursidan.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.collections4.KeyValue;
import org.apache.commons.collections4.keyvalue.DefaultKeyValue;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.Query;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.goteborg.retursidan.dao.AdvertisementDAO;
import se.goteborg.retursidan.dao.AreaDAO;
import se.goteborg.retursidan.dao.CategoryDAO;
import se.goteborg.retursidan.dao.PersonDAO;
import se.goteborg.retursidan.dao.PhotoDAO;
import se.goteborg.retursidan.dao.RequestDAO;
import se.goteborg.retursidan.dao.UnitDAO;
import se.goteborg.retursidan.model.DivisionDepartmentKey;
import se.goteborg.retursidan.model.PagedList;
import se.goteborg.retursidan.model.PhotoHolder;
import se.goteborg.retursidan.model.Year;
import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Area;
import se.goteborg.retursidan.model.entity.Category;
import se.goteborg.retursidan.model.entity.Person;
import se.goteborg.retursidan.model.entity.Photo;
import se.goteborg.retursidan.model.entity.Request;
import se.goteborg.retursidan.model.entity.Unit;
import se.vgregion.ldapservice.LdapUser;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ModelService {
    @Autowired
    private AdvertisementDAO advertisementDAO;

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private UnitDAO unitDAO;

    @Autowired
    private AreaDAO areaDAO;

    @Autowired
    private PersonDAO personDAO;

    @Autowired
    private PhotoDAO photoDAO;

    @Autowired
    private UserDirectoryService userDirectoryService;


    public void addCategory(Category category) {
        if (category.getParent() != null && category.getParent().getId() == -1) {
            category.setParent(null);
        }
        categoryDAO.add(category);
    }

    public List<Category> getCategories() {
        return categoryDAO.findAll();
    }

    public List<Category> getTopCategories() {
        return categoryDAO.findTopCategories();
    }

    public List<Category> getSubCategories(int id) {
        return categoryDAO.findAllSubCategories(id);
    }

    public void addUnit(Unit unit) {
        unitDAO.add(unit);
    }

    public List<Unit> getUnits() {
        return unitDAO.findAll();
    }

    public void addArea(Area area) {
        areaDAO.add(area);
    }

    public List<Area> getAreas() {
        return areaDAO.findAll();
    }

    public void addPerson(Person person) {
        personDAO.add(person);
    }

    public void addPhoto(Photo photo) {
        photoDAO.add(photo);
    }

    public Photo getPhoto(int id) {
        return photoDAO.findById(id);
    }

    public PhotoHolder getPhotoHolder(Integer id, boolean thumbnail) {
        Photo photo = getPhoto(id);

        if (photo == null) {
            return null;
        }

        Blob image = (thumbnail) ? photo.getThumbnail() : photo.getImage();

        PhotoHolder photoHolder = new PhotoHolder();
        photoHolder.setImage(image);
        photoHolder.setMimeType(photo.getMimeType());
        try {
            photoHolder.setImageLength(image.length());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return photoHolder;
    }

    public void copyPhotoStream(Integer id, OutputStream os, boolean thumbnail) {
        PhotoHolder photoHolder = getPhotoHolder(id, thumbnail);

        try {
            IOUtils.copyLarge(photoHolder.getImage().getBinaryStream(), os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removePhoto(Photo photo) {
        photoDAO.delete(photo);
    }

    public List<Photo> getAllPhotos() {
        return photoDAO.findAll();
    }

    public Category getCategory(int id) {
        return categoryDAO.findById(id);
    }


    public void addAdvertisement(Advertisement advertisement) {
        advertisementDAO.add(advertisement);
    }

    public Advertisement getAdvertisement(int id) {
        return advertisementDAO.findById(id);
    }

    public PagedList<Advertisement> getAllFilteredAdvertisements(Advertisement.Status status, Category topCategory,
                                                                 Category category, Unit unit, Area area,
                                                                 Boolean hidden, int page, int pageSize) {
        return advertisementDAO.find(null, status, topCategory, category, unit, area, hidden, false, page, pageSize);
    }

    public PagedList<Advertisement> getAllAdvertisements(Advertisement.Status status, Boolean hidden, int page,
                                                         int pageSize) {
        return advertisementDAO.find(null, status, null, null, null, null, hidden, false, page, pageSize);
    }

    public PagedList<Advertisement> getAllAdvertisementsForUid(String uid, Advertisement.Status status, Boolean hidden,
                                                               int page, int pageSize) {
        return advertisementDAO.find(uid, status, null, null, null, null, hidden, false, page, pageSize);
    }

    public PagedList<Advertisement> getAllFilteredAdvertisementsForUid(String uid, Advertisement.Status status,
                                                                       Category topCategory, Category category,
                                                                       Unit unit, Area area, Boolean hidden,
                                                                       int page, int pageSize) {
        return advertisementDAO.find(uid, status, topCategory, category, unit, area, hidden, false, page, pageSize);
    }

    public long publishDraftsForUid(String uid) {
        PagedList<Advertisement> usersDrafts = advertisementDAO.find(uid, Advertisement.Status.DRAFT, null, null, null,
                null, null, false, 1, Integer.MAX_VALUE);

        for (Advertisement advertisement : usersDrafts.getList()) {
            advertisement.setStatus(Advertisement.Status.PUBLISHED);
            advertisementDAO.merge(advertisement);
        }

        return usersDrafts.getTotalCount();
    }

    public void removeAdvertisement(Advertisement advertisement) {
        advertisementDAO.delete(advertisement);
    }

    public void addRequest(Request request) {
        requestDAO.add(request);
    }

    public Request getRequest(int id) {
        return requestDAO.findById(id);
    }

    public int saveRequest(Request request) {
        return requestDAO.add(request);
    }

    public PagedList<Request> getAllRequests(int page, int pageSize) {
        return requestDAO.find(null, page, pageSize);
    }

    public int saveAd(Advertisement ad) {
        return advertisementDAO.add(ad);
    }


    public void updateAd(Advertisement ad) {
        advertisementDAO.update(ad);
    }


    public void updateRequest(Request request) {
        requestDAO.update(request);
    }

    public Person getPerson(String uid) {
        return personDAO.findByUid(uid);
    }

    public void removeRequest(Request request) {
        requestDAO.delete(request);
    }

    public List<Request> getAllRequests(Request.Status status) {
        return requestDAO.findAll(status);
    }

    public void removeUnit(Unit unit) {
        unitDAO.delete(unit);
    }

    public void removeArea(Area area) {
        areaDAO.delete(area);
    }

    public void removeCategory(Category category) {
        categoryDAO.delete(category);
    }

    public void hideAdvertisement(Integer advertisementId, Boolean markAsBooked) {
        Advertisement ad = advertisementDAO.findById(advertisementId);
        ad.setHidden(true);

        if (BooleanUtils.isTrue(markAsBooked)) {
            ad.setStatus(Advertisement.Status.BOOKED);
        }

        advertisementDAO.update(ad);
    }

    public void rotatePhoto(Integer id, Function<BufferedImage, BufferedImage> postTransformFunction) {
        Photo photo = getPhoto(id);
        if (photo != null) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(photo.getImage().getBinaryStream());
                BufferedImage thumbnail = ImageIO.read(photo.getThumbnail().getBinaryStream());
                rotateImage(photo, image, thumbnail, postTransformFunction);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void rotateImage(Photo photo, BufferedImage image, BufferedImage thumbnail, Function<BufferedImage,
            BufferedImage> postTransformFunction) throws IOException, SQLException {

        BufferedImage rotated = Scalr.rotate(image, Scalr.Rotation.CW_90);
        BufferedImage rotatedThumbnail = Scalr.rotate(thumbnail, Scalr.Rotation.CW_90);

        if (postTransformFunction != null) {
            rotated = postTransformFunction.apply(rotated);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(rotated, "png", baos);

        photo.setImage(new SerialBlob(baos.toByteArray()));

        baos = new ByteArrayOutputStream();
        ImageIO.write(rotatedThumbnail, "png", baos);

        photo.setThumbnail(new SerialBlob(baos.toByteArray()));

        addPhoto(photo);
    }

    public Map<DivisionDepartmentKey, KeyValue<Year, Long>> calculateDepartmentAndDivisionGroupCount(int year) {
        /*List<Advertisement> all = advertisementDAO.findAll();

        Collector<String, ?, Map<String, Long>> stringMapCollector = Collectors.groupingBy(
                Function.identity(),
                Collectors.counting()
        );

        Map<String, Long> departmentMap = new TreeMap<>(all.stream()
                .map(ad -> ad.getDepartment() != null ? ad.getDepartment() : "okänd")
                .filter(Objects::nonNull)
                .collect(stringMapCollector));

        Map<String, Long> divisionMap = new TreeMap<>(all.stream()
                .map(ad -> ad.getDivision() != null ? ad.getDivision() : "okänd")
                .filter(Objects::nonNull)
                .collect(stringMapCollector));*/

//        int year = 2016;
        int toYear = year + 1;

        Query query = advertisementDAO.getSessionFactory().getCurrentSession().createSQLQuery(
                "select division, department, count(*) from vgr_tage_advertisement" +
                        " where created >= '" + year + "-01-01' and created < '" + toYear + "-01-01' group by division, department" +
                        " order by division, department asc"
        );

//        query.setParameter("fromDate", "2016-01-01", StandardBasicTypes.TIMESTAMP);
//        query.setParameter("toDate", "2017-01-01", StandardBasicTypes.TIMESTAMP);

        List<Object[]> rows = query.list();

        Map<DivisionDepartmentKey, KeyValue<Year, Long>> mapKeyToYearCount = new HashMap<>();

        rows.stream().forEach(row -> {
            String division = row[0] != null ? (String) row[0] : "okänd";
            String department = row[1] != null ? (String) row[1] : "okänd";
            DivisionDepartmentKey key = DivisionDepartmentKey.from(division, department); // TODO make a class for this key

            DefaultKeyValue<Year, Long> yearCount = new DefaultKeyValue<>(Year.of(year), ((BigInteger) row[2]).longValue());
//            yearCount.put(String.valueOf(year), ((BigInteger) o[2]).longValue());

            mapKeyToYearCount.put(key, yearCount);
        });

        return mapKeyToYearCount;
        /*Map<String, List<DepartmentDivisionCountTuple>> collect = list.stream().map(o -> new DepartmentDivisionCountTuple(
                (String) o[0],
                (String) o[1],
                ((BigInteger) o[2]).longValue())
        )
                .collect(Collectors.groupingBy(tuple -> tuple.getDivision() + ";" + tuple.getDepartment()));

        return collect;*/
//        return new DepartmentAndDivisionGroupCount(null, null);
    }

    public void complementAdsWithDepartmentsAndDivisions() {
        List<Advertisement> all = advertisementDAO.findAll();

//        Set<String> departments = new TreeSet<>();
//        Set<String> divisions = new TreeSet<>();

//        Map<String, String> userIdToDepartment = new HashMap<>();
//        Map<String, String> userIdToDivision = new HashMap<>();
        Map<String, LdapUser> ldapUserMap = new HashMap<>();

        all.forEach(advertisement -> {
            String creatorUid = advertisement.getCreatorUid();

            if (creatorUid == null) return;

            /*if (userIdToDepartment.containsKey(creatorUid)) {
                advertisement.setDepartment(userIdToDepartment.get(creatorUid));
                advertisement.setDivision(userIdToDivision.get(creatorUid));

                return;
            }*/

            LdapUser ldapUserByUid;
            if (ldapUserMap.containsKey(creatorUid)) {
                ldapUserByUid = ldapUserMap.get(creatorUid);
            } else {
                ldapUserByUid = this.userDirectoryService.getLdapUserByUid(creatorUid);
                ldapUserMap.put(creatorUid, ldapUserByUid);
            }

            if (ldapUserByUid == null) return;

            advertisement.setDepartment(ldapUserByUid.getAttributeValue("department"));
            advertisement.setDivision(ldapUserByUid.getAttributeValue("division"));

            updateAd(advertisement);
        });

/*        Set<SimpleLdapUser> collect = all.stream()
                .map(Advertisement::getCreatorUid)
                .filter(Objects::nonNull)
                .distinct()
                .map(userId -> (SimpleLdapUser) this.userDirectoryService.getLdapUserByUid(userId))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());*/

        /*Collector<String, ?, Map<String, Long>> stringMapCollector = Collectors.groupingBy(
                Function.identity(),
                Collectors.counting()
        );

        Map<String, Long> departmentMap = new TreeMap<>(all.stream()
                .map(ad -> ad.getDepartment() != null ? ad.getDepartment() : "okänd")
                .filter(Objects::nonNull)
                .collect(stringMapCollector));

        Map<String, Long> divisionMap = new TreeMap<>(all.stream()
                .map(ad -> ad.getDivision() != null ? ad.getDivision() : "okänd")
                .filter(Objects::nonNull)
                .collect(stringMapCollector));

        Map<String, Long>[] result = new Map[]{departmentMap, divisionMap};

        return result;*/
        /*System.out.println(departments);
        System.out.println(divisions);

        System.out.println("Departments count: " + departments.size());
        System.out.println("Divisions count: " + divisions.size());*/
    }

}
