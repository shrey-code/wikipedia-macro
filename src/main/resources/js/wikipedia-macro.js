define('wikipedia-macro',['ajs'], function(AJS){
    AJS.toInit(function () {
        console.log('Loading wiki macro');
        AJS.$('.wiki-search').hover(function(){
            AJS.$(this).parent().find('.wiki-desc').fadeIn(400);
        }, function () {
            AJS.$(this).parent().find('.wiki-desc').fadeOut(400);
        });
    });
});

require('wikipedia-macro');