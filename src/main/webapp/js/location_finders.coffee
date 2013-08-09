
sanitizeId = (ip) ->
  Strings.sanitizeId(ip)

window.getNodeListDiv = ->
  "#node_list"

window.getPreviewDiv = (ip) ->
  "#" + sanitizeId(ip)

window.getSmallScreenshotDiv = (ip) ->
  getPreviewDiv(ip) + " .small-screenshot"

window.getNodeInfoDiv = (ip) ->
  getPreviewDiv(ip) + " .node-info"

window.getPreviewSpinner = (ip) ->
  getPreviewDiv(ip) + " .spinner"

window.getPreviewTempScreenshot = (ip) ->
  getPreviewDiv(ip) + " .small-screenshot .temp-screenshot"