
window.Strings = class Strings
  constructor: ->
  @sanitizeId: (ip) ->
    ip.replace /(\.+)/g, (match) ->
      ""