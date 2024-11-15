<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Image Upload and Download</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Upload and Download Processed Image</h4>
                </div>
                <div class="card-body">
                    <form action="upload-image" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="imageFile">Choose an image to upload</label>
                            <input type="file" class="form-control-file" id="imageFile" name="imageFile" required>
                        </div>
                        <div class="form-group">
                            <label for="width">Width (px)</label>
                            <input type="number" class="form-control" id="width" name="width" min="1" required>
                        </div>
                        <div class="form-group">
                            <label for="height">Height (px)</label>
                            <input type="number" class="form-control" id="height" name="height" min="1" required>
                        </div>
                        <div class="form-group">
                            <label for="quality">Quality (%)</label>
                            <input type="number" class="form-control" id="quality" name="quality" min="1" max="100" required>
                        </div>
                        <div class="form-group">
                            <label>Select Image Format</label><br>
                            <input type="radio" id="formatWEBP" name="format" value="webp" required checked>
                            <label for="formatWEBP">WEBP</label>
                            <input type="radio" id="formatJPEG" name="format" value="jpeg" required>
                            <label for="formatJPEG">JPEG</label><br>
                            <input type="radio" id="formatPNG" name="format" value="png" required>
                            <label for="formatPNG">PNG</label><br>
                        </div>
                        <button type="submit" class="btn btn-primary btn-block">Upload and Download</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>