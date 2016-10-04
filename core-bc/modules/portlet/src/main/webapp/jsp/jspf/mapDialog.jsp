<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="mapDialog" style="display: none; ">
</div>

<div id="mapDialogImageWrapper" style="display: none;">
    <img style="" src="<%= response.encodeURL(request.getContextPath() + "/img/VGR_karta_188k.jpg") %>"/>
</div>

<script type="text/javascript">
    $(".area-info-icon").click(function (e) {
        e.preventDefault();

        var mapDialog = $("#mapDialog");
        var mapDialogImageWrapper = $("#mapDialogImageWrapper");
        mapDialog.show();
        mapDialogImageWrapper.show();
        var mapDialogImage = $("#mapDialogImageWrapper img");
        mapDialogImage.css('margin-top', -mapDialogImage.height() / 2);
        mapDialogImage.css('margin-left', -mapDialogImage.width() / 2);
    });

    $('body').bind('click', function(e){
        if($('#mapDialog').css('display') === 'block' && !jQuery(e.target).is('.area-info-icon')) {
            $('#mapDialog').hide();
            $('#mapDialogImageWrapper').hide();
        }
    });
</script>
