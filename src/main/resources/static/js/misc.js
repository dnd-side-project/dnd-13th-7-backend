(function($) {
  'use strict';
  $(function() {
    var sidebar = $('.sidebar');

    // Add active class to nav-link based on exact pathname match.
    // Using indexOf caused "/admin" to match every admin menu item.
    var currentPath = window.location.pathname.replace(/\/+$/, '') || '/';
    $('.nav li a', sidebar).each(function() {
      var $this = $(this);
      var href = $this.attr('href');

      if (!href || href.charAt(0) === '#') {
        return;
      }

      var linkPath;
      try {
        linkPath = new URL(href, window.location.origin).pathname.replace(/\/+$/, '') || '/';
      } catch (e) {
        return;
      }

      if (currentPath === linkPath) {
        $this.parents('.nav-item').last().addClass('active');
        if ($this.parents('.sub-menu').length) {
          $this.closest('.collapse').addClass('show');
          $this.addClass('active');
        }
      }
    });

    //Close other submenu in sidebar on opening any

    sidebar.on('show.bs.collapse', '.collapse', function() {
      sidebar.find('.collapse.show').collapse('hide');
    });


    //Change sidebar and content-wrapper height
    applyStyles();

    function applyStyles() {
      //Applying perfect scrollbar
      if ($('.scroll-container').length) {
        const ScrollContainer = new PerfectScrollbar('.scroll-container');
      }
    }

    //checkbox and radios
    $(".form-check label,.form-radio label").append('<i class="input-helper"></i>');


    $(".purchace-popup .popup-dismiss").on("click",function(){
      $(".purchace-popup").slideToggle();
    });
  });
})(jQuery);
