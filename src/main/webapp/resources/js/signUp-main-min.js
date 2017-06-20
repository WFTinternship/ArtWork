jQuery(document).ready(function ($) {
    function t(t, i, n) {
        animating || (i.toggleClass("is-visible", n), e(t, n), t.toggleClass("selected-table", n))
    }

    function t1(t, i, n) {
        animating || (i.toggleClass("is-visible", n), e1(t, n), t.toggleClass("selected-table", n))
    }

    function e(t, e) {
        animating = !0;
        var i = t.width(), n = t.height(), d = t.offset().top - $(window).scrollTop(), p = t.offset().left,
            w = $(".cd-form"), m = w.find(".cd-plan-info"), g = o(), v = a(), y = l(v), x = c(g), b = d - y, C = p - x,
            M = $(window).width(), k = $(window).height();
        e ? (m.html(t.html()), m.velocity({width: i + "px", height: n + "px"}, 50, function () {
            m.velocity({width: r + "px", height: s + "px"}, h, [220, 20])
        }), w.velocity({
            width: i + "px",
            height: n + "px",
            top: y,
            left: x,
            translateX: C + "px",
            translateY: b + "px",
            opacity: 1
        }, 50, function () {
            t.addClass("empty-box"), w.velocity({
                width: g + "px",
                height: v + "px",
                translateX: 0,
                translateY: 0
            }, h, [220, 20], function () {
                animating = !1, setTimeout(function () {
                    w.children("form").addClass("is-scrollable")
                }, 300)
            }).addClass("is-visible")
        })) : (w.children("form").removeClass("is-scrollable"), m.velocity({width: i + "px"}, {
            duration: u,
            easing: "easeOutCubic",
            delay: f
        }), w.velocity({width: i + "px", height: n + "px", translateX: C + "px", translateY: b + "px"}, {
            duration: u,
            easing: "easeOutCubic",
            delay: f,
            complete: function () {
                t.removeClass("empty-box"), w.velocity({
                    translateX: 0,
                    translateY: 0,
                    opacity: 0
                }, 0).find("form").scrollTop(0), animating = !1
            }
        }).removeClass("is-visible"), $(".no-csstransitions").length > 0 && t.removeClass("empty-box"))
    }

    function e1(t, e) {
        animating = !0;
        var i = t.width(), n = t.height(), d = t.offset().top - $(window).scrollTop(), p = t.offset().left,
            w = $(".cd-form1"), m = w.find(".cd-plan-info"), g = o(), v = a(), y = l(v), x = c(g), b = d - y, C = p - x,
            M = $(window).width(), k = $(window).height();
        e ? (m.html(t.html()), m.velocity({width: i + "px", height: n + "px"}, 50, function () {
            m.velocity({width: r + "px", height: s + "px"}, h, [220, 20])
        }), w.velocity({
            width: i + "px",
            height: n + "px",
            top: y,
            left: x,
            translateX: C + "px",
            translateY: b + "px",
            opacity: 1
        }, 50, function () {
            t.addClass("empty-box"), w.velocity({
                width: g + "px",
                height: v + "px",
                translateX: 0,
                translateY: 0
            }, h, [220, 20], function () {
                animating = !1, setTimeout(function () {
                    w.children("form").addClass("is-scrollable")
                }, 300)
            }).addClass("is-visible")
        })) : (w.children("form").removeClass("is-scrollable"), m.velocity({width: i + "px"}, {
            duration: u,
            easing: "easeOutCubic",
            delay: f
        }), w.velocity({width: i + "px", height: n + "px", translateX: C + "px", translateY: b + "px"}, {
            duration: u,
            easing: "easeOutCubic",
            delay: f,
            complete: function () {
                t.removeClass("empty-box"), w.velocity({
                    translateX: 0,
                    translateY: 0,
                    opacity: 0
                }, 0).find("form").scrollTop(0), animating = !1
            }
        }).removeClass("is-visible"), $(".no-csstransitions").length > 0 && t.removeClass("empty-box"))
    }

    function i() {
        var t = window.getComputedStyle(document.querySelector(".cd-form"), "::before").getPropertyValue("content").replace(/"/g, "").replace(/'/g, "");
        return t
    }

    function i1() {
        var t = window.getComputedStyle(document.querySelector(".cd-form1"), "::before").getPropertyValue("content").replace(/"/g, "").replace(/'/g, "");
        return t
    }

    function n() {
        var t = i(), e = $(".cd-form");
        if (r = "mobile" == t ? .9 * $(window).width() : 210, s = "mobile" == t ? 93 : 255, e.hasClass("is-visible")) {
            var n = o(), d = a(), h = l(d), f = c(n);
            e.velocity({width: n, height: d, top: h, left: f}, 0).find(".cd-plan-info").velocity({
                width: r + "px",
                height: s + "px"
            }, 0)
        }
    }

    function n1() {
        var t = i1(), e = $(".cd-form1");
        if (r = "mobile" == t ? .9 * $(window).width() : 210, s = "mobile" == t ? 93 : 255, e.hasClass("is-visible")) {
            var n = o(), d = a(), h = l(d), f = c(n);
            e.velocity({width: n, height: d, top: h, left: f}, 0).find(".cd-plan-info").velocity({
                width: r + "px",
                height: s + "px"
            }, 0)
        }
    }

    function o() {
        return formMaxWidth <= .9 * $(window).width() ? formMaxWidth : .9 * $(window).width()
    }

    function a() {
        return formMaxHeight <= .9 * $(window).height() ? formMaxHeight : .9 * $(window).height()
    }

    function l(t) {
        return ($(window).height() - t) / 2
    }

    function c(t) {
        return ($(window).width() - t) / 2
    }

    if ($(".cd-form").length > 0) {
        var d = i(), r = "mobile" == d ? .9 * $(window).width() : 210, s = "mobile" == d ? 93 : 255;
        formMaxWidth = 900, formMaxHeight = 650, animating = !1;
        var h = 800, f = 200, u = h - f, p = $(".cd-pricing"), w = $(".cd-overlay");
        p.on("click", ".buyer", function (e) {
            e.preventDefault(), t($(this).parents(".cd-pricing-footer").parent("li"), w, !0)
        }), $(".cd-form").on("click", ".cd-close", function (e) {
            e.preventDefault(), t(p.find(".selected-table"), w, !1)
        }), $(document).keyup(function (e) {
            "27" == e.which && t(p.find(".selected-table"), w, !1)
        }), w.on("click", function (e) {
            e.preventDefault(), t(p.find(".selected-table"), w, !1)
        }), $(window).on("resize", function () {
            requestAnimationFrame(n)
        }), $(".cd-payment-gateways").on("change", function () {
            $("#card").is(":checked") ? $(".cd-credit-card").velocity("slideDown", {duration: 300}) : $(".cd-credit-card").velocity("slideUp", {duration: 300})
        })
    }

    if ($(".cd-form1").length > 0) {
        var d = i1(), r = "mobile" == d ? .9 * $(window).width() : 210, s = "mobile" == d ? 93 : 255;
        formMaxWidth = 900, formMaxHeight = 650, animating = !1;
        var h = 800, f = 200, u = h - f, p = $(".cd-pricing"), w = $(".cd-overlay");
        p.on("click", ".artist", function (e) {
            e.preventDefault(), t1($(this).parents(".cd-pricing-footer1").parent("li"), w, !0)
        }), $(".cd-form1").on("click", ".cd-close", function (e) {
            e.preventDefault(), t1(p.find(".selected-table"), w, !1)
        }), $(document).keyup(function (e) {
            "27" == e.which && t1(p.find(".selected-table"), w, !1)
        }), w.on("click", function (e) {
            e.preventDefault(), t1(p.find(".selected-table"), w, !1)
        }), $(window).on("resize", function () {
            requestAnimationFrame(n1)
        }), $(".cd-payment-gateways").on("change", function () {
            $("#card").is(":checked") ? $(".cd-credit-card").velocity("slideDown", {duration: 300}) : $(".cd-credit-card").velocity("slideUp", {duration: 300})
        })
    }
});


$('#imageUpload').change(function(){
    readImgUrlAndPreview(this);
    function readImgUrlAndPreview(input){
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#imagePreview').attr('src', e.target.result);
            }
        };
        reader.readAsDataURL(input.files[0]);
    }
});