var map = echarts.init(document.getElementById("map"));
var option = {
    title: {
        text: 'ECharts 入门示例'
    },
    tooltip: {},
    legend: {
        data: ['服装销量']
    },
    xAxis: {
        data: ['衬衫', '羊毛衫', "雪纺衫", "裤子", "高跟鞋", "袜子"]
    },
    yAxis: {},
    series: [{
        name: '销量',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 30]
    }],
};
map.setOption(option);
//文件下载，不能通过ajax请求，否则会存储在javascript的内存中，javascript不能访问文件。
$(".export").click(function () {
    var param = {};
    param.title = "柱状图导出";
    param.description = "这是描述";
    param.img = map.getDataURL({
        type: "png",
        backgroundColor: "white"
    });
    var url ="/app/file/export";
    var form = $("<form></form>").attr("action",url).attr("method","post");
    form.append($("<input></input>").attr("type","hidden").attr("value",param.title).attr("name","title"));
    form.append($("<input></input>").attr("type","hidden").attr("value",param.description).attr("name","description"));
    form.append($("<input></input>").attr("type","hidden").attr("value",param.img).attr("name","img"));
    form.appendTo("body").submit().remove();
});
