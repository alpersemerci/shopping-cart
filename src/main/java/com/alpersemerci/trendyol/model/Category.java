package com.alpersemerci.trendyol.model;

import lombok.*;

/**
 * Category model object
 *
 * @author Alper Semerci
 */
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Category {

    @NonNull
    private String title;

    private Category parentCategory;

    public Boolean hasParent() {
        return parentCategory != null;
    }

}
