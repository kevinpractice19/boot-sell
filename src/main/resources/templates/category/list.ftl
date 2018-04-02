<html>
<head>
    <link href="https://cdn.bootcss.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>类目id</th>
                    <th>名字</th>
                    <th>type</th>
                    <th> 创建时间</th>
                    <th> 修改时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#list categoryList as category>
                <tr>
                    <td>${category.categoryId}</td>
                    <td>${category.categoryName}</td>
                    <td>${category.categoryType}</td>
                    <td>${category.createTime}</td>
                    <td>${category.updateTime}</td>
                    <td><a href="/seller/category/findOne?categoryId=${category.categoryId}">修改</a></td>
                </tr>
                </#list>
                </tbody>
            </table>

</body>
</html>