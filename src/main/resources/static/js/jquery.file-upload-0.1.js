/**
 * Created by Administrator on 2019/7/3.
 */
;(function ($) {
  var _settings = {
      bytesPerPiece: 1024*1024*10,//单个文件大小:10M
      maxFileSize: 1024*1024*1024,   //总文件大小:1G
  };
  var stateCode = '200';    //状态码:200-准备就绪,201-上传中,206-上传成功,207-上传失败
  var substate = [];
  $.fn.extend({
      submitLargeFileForm: function (settings,handler) {
          if(stateCode == '201'){
              return false;
          }
          stateCode = '201';
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
              result.msg = '上传的文件不能超过'+(_settings.maxFileSize/1024/1024)+"MB";
              handlerFunc.call(window,result);
              return this;
          }
          var start = 0;
          var end=0;
          var index = 0;
          while(start<fileSize && stateCode == '201'){
              var fm = new FormData();
              fm.set('index',index);
              substate[index] = false;
              if(start+_settings.bytesPerPiece<fileSize){
                  end = start+_settings.bytesPerPiece;
              }else{
                  end = fileSize;
                  fm.set('finished','1');
              }
              var chunk = blobFile.slice(start,end);
              fm.append("file",chunk,fileName+".part"+index);
              $.ajax({
                  'url': _settings.url,
                  'type': 'POST',
                  'cache': false,
                  'data': fm,
                  'processData': false,
                  'contentType':false,
                  'success': function(result){
                      if(result.code == '10000'){
                          substate[index] = true;
                          if(substate.every(value=>value)){
                              stateCode = '206';
                              handler.call(window,result);
                          }
                      }else{
                          stateCode = '207';
                          handlerFunc.call(window,result);
                      }
                  },
                  'fail': function (result) {
                      stateCode = '207';
                      handlerFunc.call(window,result);
                  }
              });
              start = end;
              index++;
          }
      }
  })
})(jQuery)



