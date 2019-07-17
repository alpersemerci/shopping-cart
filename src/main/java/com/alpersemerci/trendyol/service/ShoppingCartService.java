package com.alpersemerci.trendyol.service;

import com.alpersemerci.trendyol.model.*;

import java.util.*;

/**
 * Service class responsible from shopping cart operations.
 *
 * @author Alper Semerci
 * @see ShoppingCart
 */
public class ShoppingCartService {

    private ProductService productService = new ProductService();

    /**
     * Adds product to given shopping chart with given quantity.
     *
     * @param cart     Shopping cart
     * @param product  Product to add
     * @param quantity Quantity of product
     */
    public void addItemToShoppingCart(ShoppingCart cart, Product product, Integer quantity) {
        cart.getContents().put(product, quantity);
        cart.setTotalAmount(cart.getTotalAmount() + product.getPrice() * quantity);
    }

    /**
     * Applies campaign discount to given shopping chart for multiple campaigns.
     *
     * @param cart      Shopping cart
     * @param campaigns Array of campaigns
     */
    public void applyCampaignDiscount(ShoppingCart cart, Campaign... campaigns) {
        Arrays.asList(campaigns).forEach(campaign -> applyCampaignDiscount(cart, campaign));
    }

    /**
     * Applies campaign discount to given shopping chart for single campaign.
     *
     * @param cart     Shopping cart
     * @param campaign Category campaign
     */
    public void applyCampaignDiscount(ShoppingCart cart, Campaign campaign) {
        List<Product> productsSuitableForDiscount = findProductsSuitableForDiscount(cart, campaign);
        productsSuitableForDiscount.forEach(product -> {
            Double discount = calculateDiscountTotal(cart, product, campaign);
            cart.setCampaignDiscount(cart.getCampaignDiscount() + discount);
            cart.setTotalAmountAfterDiscounts(cart.getTotalAmount() - cart.getCampaignDiscount());
        });
    }

    /**
     * Applies coupon to given shopping chart.
     *
     * @param cart   Shopping cart
     * @param coupon Coupon
     */
    public void applyCoupon(ShoppingCart cart, Coupon coupon) {
        if (cart.getTotalAmountAfterDiscounts() > coupon.getCouponLimit()) {
            cart.setCouponDiscount(coupon.getDiscount());
            cart.setTotalAmountAfterDiscounts(cart.getTotalAmount() - (cart.getCampaignDiscount() + coupon.getDiscount()));
        }
    }

    /**
     * Prints given chart details to console.
     *
     * @param cart
     */
    public void printCart(ShoppingCart cart) {
        Map<Category, List<Product>> categoryListMap = groupProductsByCategories(cart);
        categoryListMap.forEach((category, products) -> {
            System.out.println();
            System.out.println("-----------------------");
            System.out.println(category.getTitle());
            System.out.println("-----------------------");
            products.forEach(product -> {
                System.out.print("\t * ");
                System.out.print(product.getTitle());
                System.out.print("\t");
                System.out.print(cart.getContents().get(product));
                System.out.print("\t");
                System.out.print(product.getPrice());
                System.out.print("\t");
                System.out.print(product.getPrice() * cart.getContents().get(product));
                System.out.println();
            });
        });

        System.out.println();
        System.out.println("Total AMOUNT : " + cart.getTotalAmount());
        System.out.println("Campaign Discount : " + cart.getCampaignDiscount());
        System.out.println("Coupon Discount : " + cart.getCouponDiscount());
        System.out.println("Total AMOUNT After Discounts: " + cart.getTotalAmountAfterDiscounts());
    }

    /**
     * Returns all distinct categories of products in shopping cart.
     *
     * @param cart Shopping cart
     * @return Set of categories
     */
    public Set<Category> findDistinctProductCategoriesInCart(ShoppingCart cart) {
        Set<Category> categories = new HashSet<>();
        cart.getContents().keySet().forEach(product -> {
            Set<Category> allCategoriesOfProduct = productService.getAllCategoriesOfProduct(product);
            categories.addAll(allCategoriesOfProduct);
        });
        return categories;
    }

    /**
     * Groups all products in shopping cart by their categories.
     *
     * @param cart Shopping cart
     * @return Map of category and product list.
     */
    private Map<Category, List<Product>> groupProductsByCategories(ShoppingCart cart) {
        Map<Category, List<Product>> categoryProductListMap = new HashMap<>();
        cart.getContents().keySet().forEach(product -> {
            List<Product> products = categoryProductListMap.getOrDefault(product.getCategory(), new ArrayList<>());
            products.add(product);
            categoryProductListMap.put(product.getCategory(), products);
        });
        return categoryProductListMap;
    }

    /**
     * Calculates discount total for given product in shopping chart.
     *
     * @param cart Shopping chart
     * @param product Product
     * @param campaign Campaign
     * @return
     */
    private Double calculateDiscountTotal(ShoppingCart cart, Product product, Campaign campaign) {
        Integer productCount = cart.getContents().get(product);
        Double totalProductPrice = productCount * product.getPrice();
        Double discount = campaign.getDiscount();

        if (DiscountType.RATE.equals(campaign.getDiscountType())) {
            return totalProductPrice * discount / 100;
        } else if (DiscountType.AMOUNT.equals(campaign.getDiscountType())) {
            return discount;
        }
        return 0d;
    }

    /**
     * Returns list of products in shopping cart which are suitable for campaign.
     *
     * @param cart Shopping cart
     * @param campaign Campaign
     * @return List of suitable products for campaign
     */
    private List<Product> findProductsSuitableForDiscount(ShoppingCart cart, Campaign campaign) {
        List<Product> products = new ArrayList<>();
        cart.getContents().keySet().forEach(product -> {
            if (isProductEligibleForCampaign(product, cart, campaign)) {
                products.add(product);
            }
        });
        return products;
    }

    /**
     * Checks is given product is suitable for campaign or not.
     *
     * @param product Product
     * @param cart Shopping cart
     * @param campaign Campaign
     * @return Boolean result of if product eligible or not
     */
    private Boolean isProductEligibleForCampaign(Product product, ShoppingCart cart, Campaign campaign) {
        Set<Category> allCategoriesOfProduct = productService.getAllCategoriesOfProduct(product);
        if (allCategoriesOfProduct.contains(campaign.getCategory())) {
            Integer productCount = cart.getContents().get(product);
            if (productCount > campaign.getDiscountLimit()) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
