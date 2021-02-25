<html>
<head>
    <meta charset="UTF-8">
    <title>成功提示</title>
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <div class="alert">
                <button type="button" class="close" data-dismiss="alert">×</button>
                <h4>
                    提示!
                    <#--字段默认值 ${msg!""-->
                </h4> <strong>${msg!""}</strong> <a href="${url}" class="alert-link">alert-link</a>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    setTimeout('location.href="${url}"', 3000);
</script>
</html>