self.addEventListener('message', function (event) {
    if(event.data.method == null) self.postMessage('参数错误！未找到method参数！');
    if(event.data.method === 'initValidateTable') {
        self.postMessage({
            type: 'initValidateTable',
            tableName: event.data.args.tableName,
            ranges: initValidateTable(event.data.args)
        });
    }
},false);

function initValidateTable(tableObj)
{
    let ranges = [];
    const columns = JSON.parse(tableObj.columns);
    tableObj.getSettings.mustColumnsPropArr
        .forEach((item)=>{
            const col = propToCol(columns,item);
            for(let row = 0; row < tableObj.srcData.length; row++){
                const cellData = tableObj.srcData[row][item];
                if(cellData == null || cellData === '')
                    ranges.push([row,col,row,col]);
            }
        });
    const conValColArr = tableObj.getSettings.conditionValColArr;
    if(conValColArr != null) {
        conValColArr.forEach((item) => {
            for (let row = 0; row < tableObj.srcData.length; row++) {
                let flag = item.logical === 'and';
                item.condition.forEach((j) => {
                    const cellData = tableObj.srcData[row][j[0]];
                    const bo = (
                        (j[1] === '=' && cellData === j[2]) ||
                        (j[1] === '!=' && cellData !== j[2]) ||
                        (j[1] === 'isNull' && (cellData == null || cellData === '')) ||
                        (j[1] === 'notNull' && (cellData != null && cellData !== ''))
                    );
                    if (item.logical === 'and') flag = flag && bo;
                    else flag = flag || bo;
                });
                item.target.forEach((k) => {
                    const col = propToCol(columns, k);
                    const targetCellData = tableObj.srcData[row][k];
                    if (flag && (targetCellData == null || targetCellData === '')) ranges.push([row, col, row, col]);
                });
            }
        });
    }
    return ranges;
}

function propToCol(columns,prop) {
    for(let i=0;i<columns.length;i++)
        if(columns[i].data === prop) return i;
    return -1;
}