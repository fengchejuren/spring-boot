/**
 * Created by Administrator on 2019/7/3.
 * 大文件分片上传
 */
;(function ($) {
    var _settings = {
        bytesPerPiece: 1024*1024*10,//单个文件大小:10M
        maxFileSize: 1024*1024*1024,   //总文件大小:1G
        keyFileName: '',  //提交到后台的文件名,建议加上时间戳.防止后台有提交同名的文件时可能读取不是目标文件
        showProgress: function (percent,total) {
         console.log('文件大小为'+parseInt(total/1024/1024)+"M,已经上传"+percent)
         }, //显示进度函数,可以重写此方法
        acceptType: '',   //允许接收的文件类型,默认为所有类型
    };
    var stateCode = '200';    //状态码:200-准备就绪,201-上传中,206-上传成功,207-上传失败
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
            var percent = 0;
            if(fileSize>_settings.maxFileSize){
                stateCode = '207';
                var result = {};
                result.code = '99999';
                result.msg = '上传的文件不能超过'+(_settings.maxFileSize/1024/1024)+"MB";
                handler.call(window,result);
                return this;
            }
            var postf = fileName.substring(fileName.lastIndexOf("."));//文件格式,比如说.zip/.gif等
            if(!!_settings.acceptType){
                if(_settings.acceptType.indexOf(postf) == -1){
                    stateCode = '207';
                    var result = {};
                    result.code = '99999';
                    result.msg = '请上传'+_settings.acceptType+'格式的文件';
                    handler.call(window,result);
                    return this;
                }
            }
            var start = 0;
            var end=0;
            var index = 0;
            while(start<fileSize && stateCode == '201'){
                var fm = new FormData();
                fm.set('index',index);
                if(start+_settings.bytesPerPiece<fileSize){
                    end = start+_settings.bytesPerPiece;
                }else{
                    end = fileSize;
                    fm.set('finished','1');
                }
                var chunk = blobFile.slice(start,end);
                fm.append("file",chunk,(!!_settings.keyFileName?(_settings.keyFileName+postf):fileName)+".part"+index);
                $.ajax({
                    'url': _settings.url,
                    'type': 'POST',
                    'cache': false,
                    'data': fm,
                    xhr: function(){
                        var myXhr = $.ajaxSettings.xhr();          //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数
                        if(myXhr.upload){            //绑定progress事件的回调函数
                            if(typeof _settings.showProgress  == 'function'){
                                myXhr.upload.addEventListener('progress',function (e) {
                                    if( e.loaded/e.total > percent){
                                        percent = e.loaded/e.total;
                                        _settings.showProgress.call(window,Number(percent*100).toFixed(0)+"%",fileSize);
                                    }
                                }, false);
                            }
                        }    //xhr对象返回给jQuery使用
                        return myXhr;
                    },
                    'processData': false,
                    'contentType':false,
                    'success': function(result){
                        if(result.code == '10000'){
                            stateCode = '206';
                            handler.call(window,result);
                        }else if(result.code != '10003'){
                            stateCode = '207';
                            handler.call(window,result);
                        }
                    },
                    'fail': function (result) {
                        stateCode = '207';
                        handler.call(window,result);
                    }
                });
                start = end;
                index++;
            }
        },
        getState: function () {
            return stateCode;
        }
    })
})(jQuery)



