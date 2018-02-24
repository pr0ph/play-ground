var performanceData =
    [{
        "ticker": "IVV",
        "assetClassCode": "EQ",
        "assetClassName": "Equity",
        "name": "iShares Core S&P 500 ETF",
        "returns1Y": 1.74,
        "returns2Y": 1.2,
        "returns3Y": 2.2,
        "returns4Y": -0.2,
        "returns5Y": 0.6
    }, {
        "ticker": "EFA",
        "assetClassCode": "EQ",
        "assetClassName": "Equity",
        "name": "iShares MSCI EAFE ETF",
        "returns1Y": 1.74,
        "returns2Y": 1.2,
        "returns3Y": 2.2,
        "returns4Y": -0.2,
        "returns5Y": 0.6
    }, {
        "ticker": "AGG",
        "assetClassCode": "FI",
        "assetClassName": "Fixed Income",
        "name": "iShares Core U.S. Aggregate Bond ETF",
        "returns1Y": 1.24,
        "returns2Y": 2.2,
        "returns3Y": 2.9,
        "returns4Y": -0.4,
        "returns5Y": 0.9
    }, {
        "ticker": "IWF",
        "assetClassCode": "EQ",
        "assetClassName": "Equity",
        "name": "iShares Russell 1000 Growth ETF",
        "returns1Y": 3.14,
        "returns2Y": 3.1,
        "returns3Y": 4.2,
        "returns4Y": -0.4,
        "returns5Y": 0.2
    }, {
        "ticker": "SLV",
        "assetClassCode": "COMM",
        "assetClassName": "Commodities",
        "name": "iShares Silver Trust",
        "returns1Y": 3.74,
        "returns2Y": 2.1,
        "returns3Y": 5.2,
        "returns4Y": 2.4,
        "returns5Y": 9.0
    }];

// This function is only needed in Question 3
function sortObj(list, key, order) {

    let stringKeys = ["ticker", "fundName", "assetClass"];

    function compareInt(a, b) {
        a = a[key];
        b = b[key];
        return (order === 'asc') ? a-b : b-a;
    }

    function compareString(a, b) {
        a = a[key];
        b = b[key];

        let diff = 0;
        if (a < b) {
            diff = 1;
        } else if (a > b) {
            diff = -1;
        }

        return (order === 'asc') ? diff : (-1)*diff;
    }

    if (stringKeys.indexOf(key) !== -1) {
        return list.sort(compareString);
    }
    return list.sort(compareInt);
}

function addEventListener() {
    $(".headers").click(function() {
        let key = $(this).data("key");
        let order = $(this).data("order");
        if (order === "asc") {
            $(this).data("order", "desc");
            // fa-sort-up class should be changed to fa-sort-down
        } else {
            $(this).data("order", "asc");
            // fa-sort-down class should be changed to fa-sort-up
        }
        var sortedData = sortObj(performanceData, key, order);

        var table = document.getElementById("fundData");
        var headerEl = document.querySelector("tr");
        table.innerHTML = headerEl;
        loadPerformanceDataIntoTable(sortedData);
    });
}

// Add your javascript here
function loadPerformanceDataIntoTable(data) {
    var content = "";
    for (let i = 0; i < performanceData.length; i++) {
        content = content +
            `<tr>
                <td>${data[i].ticker}</td>
                <td>${data[i].name}</td>
                <td>${data[i].assetClassName}</td>
                <td>${data[i].returns1Y}</td>
                <td>${data[i].returns2Y}</td>
                <td>${data[i].returns3Y}</td>
                <td>${data[i].returns4Y}</td>
                <td>${data[i].returns5Y}</td>
            </tr>`;
    }
    var table = document.getElementById("fundData");
    table.innerHTML += content;
}

document.addEventListener("DOMContentLoaded", function(event) {
    console.log(performanceData);
    console.log(sortObj(performanceData, "ticker", "desc"));
    addEventListener();
    loadPerformanceDataIntoTable(performanceData);
});
