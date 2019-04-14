const DataSol = {
    fixdata (data){
        var o = "",
            l = 0,
            w = 10240;
        for (; l < data.byteLength / w; ++l)
            o += String.fromCharCode.apply(
                null,
                new Uint8Array(data.slice(l * w, l * w + w))
            );
        o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
        return o;
    },
    solveSheet (tbdata, sheetArr) {
        var data = [];
        //获取表头
        //获取数据
        var length = sheetArr.length;
        for (var i = 1; i < length; i++) {
            var t = {};
            for (var j = 0; j < sheetArr[0].length; j++) {
                t["col" + j] = sheetArr[i][j];
            }
            t.key = t.col0;
            data.push(t);
            tbdata.push(t);
        }
        console.log("tbdata", tbdata);
        return data;
    },
}
export default DataSol;