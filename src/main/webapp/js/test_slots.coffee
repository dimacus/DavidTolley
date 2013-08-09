window.BrowserIcon = class BrowserIcon
  constructor: ->

  @getBrowserIcon: (browser) ->
    switch browser
      when "firefox" then "firefox.png"
      when "chrome"  then "chrome.png"
      when "internet explorer" then "internet_explorer.png"
      else "firefox.png"


window.TestSlots = class TestSlots
  SLOTS_BY_BROWSER = {}

  constructor: ->

  addSlot: (browser, slot) ->
    if not SLOTS_BY_BROWSER[browser]?
      SLOTS_BY_BROWSER[browser] = {used: 0, total: 0}

    if slot.session == ""
      SLOTS_BY_BROWSER[browser].total = SLOTS_BY_BROWSER[browser].total + 1
    else
      SLOTS_BY_BROWSER[browser].total = SLOTS_BY_BROWSER[browser].total + 1
      SLOTS_BY_BROWSER[browser].used = SLOTS_BY_BROWSER[browser].used + 1

  getHtml: ->
    keys = Object.keys(SLOTS_BY_BROWSER)
    console.log keys

    html = "<dt class='test-slots'>Used</dt>"
    html = html + "<dd><dl>"

    for browser in keys
      html = html +  "<dt><img class='browser-icon' src='/plugin/SeleniumGridExtras-plugin/images/"
      html = html + BrowserIcon.getBrowserIcon(browser) + "' /></dt>"
      html = html +  "<dd>"
      html = html +  SLOTS_BY_BROWSER[browser].used + "/" + SLOTS_BY_BROWSER[browser].total
      html = html +  "</dd>"

    html = html +  "</dl></dd>"
    html





  getSlot: (slot) ->
    SLOTS_BY_BROWSER[slot]
