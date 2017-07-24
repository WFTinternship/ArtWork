package am.aca.wftartproject.controller.util;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ASUS on 23-Jul-17
 */
public class TestRedirectAttributes implements RedirectAttributes {
    Map<String,Object> stringMap = new LinkedHashMap<>();

    @Override
    public RedirectAttributes addAttribute(String attributeName, Object attributeValue) {
        return null;
    }

    @Override
    public RedirectAttributes addAttribute(Object attributeValue) {
        return null;
    }

    @Override
    public RedirectAttributes addAllAttributes(Collection<?> attributeValues) {
        return null;
    }

    @Override
    public Model addAllAttributes(Map<String, ?> attributes) {
        return null;
    }

    @Override
    public RedirectAttributes mergeAttributes(Map<String, ?> attributes) {
        return null;
    }

    @Override
    public boolean containsAttribute(String attributeName) {
        return false;
    }

    @Override
    public Map<String, Object> asMap() {
        return null;
    }

    @Override
    public RedirectAttributes addFlashAttribute(String attributeName, Object attributeValue) {
        stringMap.put(attributeName,attributeValue);
        return null;
    }

    @Override
    public RedirectAttributes addFlashAttribute(Object attributeValue) {
        return null;
    }

    @Override
    public Map<String, ?> getFlashAttributes() {
        return stringMap;
    }
}
