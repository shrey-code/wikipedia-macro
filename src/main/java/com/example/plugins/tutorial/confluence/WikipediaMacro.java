package com.example.plugins.tutorial.confluence;

import aQute.bnd.annotation.component.Component;
import com.atlassian.cache.Cache;
import com.atlassian.cache.CacheManager;
import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import com.atlassian.confluence.util.velocity.VelocityUtils;
//import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ConfluenceImport;
import com.google.common.collect.ImmutableMap;

import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

@Component
public class WikipediaMacro implements Macro {

    private Cache<String, Optional<WikipediaResponse>> cache;

    @Inject
    public WikipediaMacro(WikipediaService service, @ConfluenceImport CacheManager cacheManager) {
        cache = cacheManager.getCache("wikipedia-macro", service::searchByText);
    }

    @Override
    public String execute(Map<String, String> map, String s, ConversionContext conversionContext) throws MacroExecutionException {
        Map context = ImmutableMap.builder().put("wiki", cache.get(map.get("search")).orElseThrow(
                () -> new MacroExecutionException("Unable to retrieve response from Wikipedia"))).putAll(map).build();
        return VelocityUtils.getRenderedTemplate("templates/wikipedia-macro.vm", context);
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.NONE;
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.INLINE;
    }


}