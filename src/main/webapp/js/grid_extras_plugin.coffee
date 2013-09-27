jQuery.noConflict()

curentUrl = window.location.pathname;


sanitizeScreenshot = (image) ->
  image.gsub /[\\\"\[\]]/, ""

addPreviewDiv = (node) ->
  li = "<li><div id='" + Strings.sanitizeId(node.host) + "' class='node-list'><div class='small-screenshot'></div><dl class='node-info'></dl><div class='spinner'></div></li>"
  jQuery(getNodeListDiv()).append(li)

addTemporaryComputerIcon = (ip) ->
  tempIcon = "<img class='temp-screenshot' src='/plugin/SeleniumGridExtras-plugin/images/computer.png'/>"
  jQuery(getSmallScreenshotDiv(ip)).append tempIcon

addHostNameToPreviewDiv = (ip) ->
  jQuery(getNodeInfoDiv(ip)).append "<dt>Host</dt><dd>" + ip + "</dd>"


addLoadingSpinnerToPreview = (node) ->
  spinnerImage = "<img src='/plugin/SeleniumGridExtras-plugin/images/loading.gif'/>"
  jQuery(getPreviewSpinner(ip)).append spinnerImage

hideLoadingSpinnerFromPreview = (ip) ->
  jQuery(getPreviewSpinner(ip)).hide()

addSmallScreenshotToPreview = (ip, width, height) ->
  jQuery.ajax curentUrl + "screenshot?width=" + width + "&height=" + height + "&keep=false&ip=" + ip,
    type: 'GET',
    dataType: 'json',
    success: (screenshot, textStatus, jqXHR) ->

      actualScreenshot = "<img class='actual-screenshot' src='data:image/png;base64,"
      actualScreenshot = actualScreenshot + sanitizeScreenshot(screenshot.image)
      actualScreenshot = actualScreenshot + "' >"
      jQuery(getPreviewTempScreenshot(ip)).hide()
      jQuery(getSmallScreenshotDiv(ip)).append actualScreenshot


addFullScreenshot = (ip, width, height) ->
  jQuery.ajax curentUrl + "screenshot?width=" + width + "&height=" + height + "&keep=false&ip=" + ip,
    type: 'GET',
    dataType: 'json',
    success: (screenshot, textStatus, jqXHR) ->

      actualScreenshot = "<img class='full-screenshot' src='data:image/png;base64,"
      actualScreenshot = actualScreenshot + sanitizeScreenshot(screenshot.image)
      actualScreenshot = actualScreenshot + "' >"
      jQuery(getLargeScreenshotDiv() + " img").remove()
      jQuery(getLargeScreenshotDiv()).append actualScreenshot


addTestSlots = (node) ->
  gridTestSlots = new TestSlots

  for slot in node.slots
    gridTestSlots.addSlot(slot.browserName, slot)

  jQuery(getNodeInfoDiv(node.host)).append gridTestSlots.getHtml()

addTestSlotsStatus = (node) ->
  status = "idle"

  for slot in node.slots
    if slot.session != ""
      status = "busy"

  jQuery(getNodeInfoDiv(node.host)).append "<dt>Status</dt><dd>" + status + "</dd>"

attachFullScreenshotTrigger = (ip) ->
  console.log "attaching screenshot get event to " + ip
  jQuery(getPreviewDiv(ip)).click ->
    console.log "Clicked on " + ip + " now getting full screenshot"
    addFullScreenshot ip, 768, 1024



addPreviewNode = (node) ->
  addPreviewDiv node
  addTemporaryComputerIcon node.host
  addHostNameToPreviewDiv node.host
  addSmallScreenshotToPreview node.host, 200, 100
  attachFullScreenshotTrigger node.host
  addTestSlotsStatus node
  hideLoadingSpinnerFromPreview node.host



setupPreviewNodes = (callback) ->
  jQuery.ajax curentUrl + "nodes",
                type: 'GET'
                dataType: 'json'
                error: (jqXHR, textStatus, errorThrown) ->
                  alert "Something went wrong getting nodes - AJAX Error:#{textStatus}"
                success: (returnedNodes, textStatus, jqXHR) ->
                    callback(returnedNodes)






jQuery('document').ready ->

  setupPreviewNodes (nodes) ->
    for node in nodes
      console.log "Working on " + node.host
      addPreviewNode (node)








