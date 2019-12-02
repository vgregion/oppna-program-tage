package se.goteborg.retursidan.portlet.controller;

import org.imgscalr.Scalr;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.portlet.context.PortletContextAware;
import se.goteborg.retursidan.model.PhotoHolder;

import javax.portlet.PortletContext;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.function.Function;

/**
 * @author Patrik Bergström
 */
public abstract class PhotoBaseController extends BaseController implements PortletContextAware {

    protected void outputImage(ResourceResponse response, Integer id, boolean thumbnail) {
        if (id != null) {
//			Photo photo = null;
            try {
//				photo = modelService.getPhoto(id);
                PhotoHolder photoHolder = modelService.getPhotoHolder(id, thumbnail);
                if (photoHolder != null) {
                    if (photoHolder.getMimeType() != null) {
                        response.setContentType(photoHolder.getMimeType());
                    }
//                    Blob image = photoHolder.getImage();
                    response.setContentLength((int) photoHolder.getImageLength());
                    OutputStream os = response.getPortletOutputStream();
                    modelService.copyPhotoStream(id, os, thumbnail);
//                    IOUtils.copyLarge(image.getBinaryStream(), os);
                    os.close();
                } else {
                    logger.trace("Could not find " + (thumbnail ? "thumbnail" : "photo") + " with id = '" + id +"' in database.");
                    outputDefault(response, thumbnail);
                }
            } catch (Exception e) {
                logger.warn("Could not write " + (thumbnail ? "thumbnail" : "photo") + " with id = '" + id + "' to output stream.", e);
                outputDefault(response, thumbnail);
            }
        } else {
            logger.trace("No id specified for " + (thumbnail ? "thumbnail" : "photo") + " requested.");
            outputDefault(response, thumbnail);
        }
    }

    protected void outputDefault(ResourceResponse response, boolean thumbnail) {
        // photo could not be found; serve up a default photo
        logger.trace("Requested " + (thumbnail ? "thumbnail" : "photo") + " could not be retrieved, so a default photo will be served up instead.");
        try {
            String path = "/img/pic_missing" + ((thumbnail) ? "_thumb" : "") + ".png";
            InputStream is = getPortletContext().getResource(path).openStream();
            response.setContentType("image/png");
            OutputStream os = response.getPortletOutputStream();
            byte[] buf = new byte[1024];
            while(is.read(buf) != -1) {
                os.write(buf);
            }
            is.close();
            os.close();
        } catch (Exception e) {
            logger.error("Error while serving up default photo.", e);
        }
    }

    @ResourceMapping("photo")
    public void servePhoto(ResourceResponse response, @RequestParam("id") int id) {
        outputImage(response, id, false);
    }

    @ResourceMapping("thumbnail")
    public void serveThumbnail(ResourceResponse response, @RequestParam(required=false, value = "id") Integer id) {
        outputImage(response, id, true);
    }

    @ResourceMapping("rotatePhoto")
    public void rotatePhoto(ResourceRequest request, ResourceResponse response,
                            @RequestParam(required=false, value = "id") Integer id) {
        if (id != null) {
            try {
                Function<BufferedImage, BufferedImage> postTransformFunction = bufferedImage -> {
                    int maxWidth = getConfig(request).getImageWidthInt();

                    if (bufferedImage.getWidth() > maxWidth) {
                        return Scalr.resize(
                                bufferedImage,
                                Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH,
                                maxWidth,
                                getConfig(request).getImageHeightInt()
                        );
                    } else {
                        return bufferedImage;
                    }
                };

                modelService.rotatePhoto(id, postTransformFunction);

                try {
                    PrintWriter writer = response.getWriter();
                    writer.print("{\"success\": true, \"id\": " + id + "}");
                } catch(IOException e) {
                    logger.error("Could not write to response output stream!", e);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    abstract public PortletContext getPortletContext();
}
