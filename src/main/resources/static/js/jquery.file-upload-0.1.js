/**
 * Created by Administrator on 2019/7/3.
 */
;(function ($) {
  var _settings = {
      bytesPerPiece: 1024*1024*10,//单个文件大小:10M
      maxFileSize: 1024*1024*1024,   //总文件大小:1G
  };
  $.fn.extend({
      submitLargeFileForm: function (settings,handlerFunc) {
          Object.assign(_settings,settings);
          //获取表单中的文件
          var file;
          if(_settings.file){
              file = $(_settings.file);
          }else{
              file = $(this).find('input[type=file]')[0]
          }
          var name = $(file).attr('name');  //表单name
          var blobFile = file.files[0];
          var fileSize = blobFile.size;
          var fileName = blobFile.name;
          if(fileSize>_settings.maxFileSize){
              var result = {};
              result.code = '99999';
              result.msg = '上传的文件不能超过'+(_settings.maxFileSize/1024/1024)+"M";
              handlerFunc.call(window,result);
              return this;
          }
          var start = 0;
          var end=0;
          var index = 0;
          while(start<fileSize){
              if(start+_settings.bytesPerPiece<=fileSize){
                  end = start+_settings.bytesPerPiece;
              }else{
                  end = fileSize;
              }
              var chunk = blobFile.slice(start,end);
              var formData = new FormData(this);
              formData.append("file","123");
              $.ajax({
                  url: _settings.url,
                  type: 'post',
                  cache: false,
                  data: {'file':'123'},
                  success: function(result){
                      handlerFunc.call(window,result);
                      return this;
                  },
                  fail: function (result) {
                      handlerFunc.call(window,result);
                      return this;
                  }
              });
              start = end;
              index++;
          }
      }
  })
})(jQuery)



