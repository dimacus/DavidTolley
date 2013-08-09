jQuery.noConflict();

jQuery('document').ready(function () {
    var currentUrl = window.location.pathname;
    jQuery.ajax({
                    dataType: "json",
                    url: currentUrl + "nodes",
                    success: function (nodeList) {
                        jQuery.each(nodeList, function (index, value) {
                            initialLoadGridNodeIcons(value);

                        });
                    }
                });
});

function initialLoadGridNodeIcons(node) {
    var nodeHtml = getHtmlForNode(node.host);

    jQuery('#node_list').append(nodeHtml);

}

function getHtmlForNode(nodeId) {
    var currentUrl = window.location.pathname;
    var screenshot = "";

    var divHtml = "<li>";
        divHtml = divHtml + "<div class='node-list' id='" + nodeId + "'>";
            divHtml = divHtml + "<div class='small-screenshot' id='screenshot-div-" + nodeId + "'>";

                  jQuery.ajax({
                        dataType: "json",
                        url: currentUrl + "screenshot?ip=" + nodeId + "&width=200&height=100&keep=false",
                        success: function(response){

                            screenshot = response.image;
                        }
                  });

//                divHtml = divHtml + "<img class='temp-node-icon' src='data:image/png;base64," + screenshot +  "' />";
                divHtml = divHtml + "<img class='temp-node-icon' src='/plugin/SeleniumGridExtras-plugin/images/computer.png' />";
            divHtml = divHtml + "</div>";
            divHtml = divHtml + "Host: " + nodeId;

        divHtml = divHtml + "</div>";
    divHtml = divHtml + "</li>";
    return divHtml;
}