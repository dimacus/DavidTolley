// Generated by CoffeeScript 1.6.3
(function() {
  var Strings;

  window.Strings = Strings = (function() {
    function Strings() {}

    Strings.sanitizeId = function(ip) {
      return ip.replace(/(\.+)/g, function(match) {
        return "";
      });
    };

    return Strings;

  })();

}).call(this);
