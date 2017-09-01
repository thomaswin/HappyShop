package com.sephora.happyshop.ui.product;

import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.data.source.LoadDataCallback;
import com.sephora.happyshop.service.CartManager;
import com.sephora.happyshop.service.ProductManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Thomas Win on 31/8/17.
 */
public class ProductDetailPresenterTest {

    public static final Product PRODUCT = new Product(1, "Name1", "Category1", 10.00, "image_url1", "Description1", false);

    @Mock
    private ProductManager productManager;

    @Mock
    private CartManager cartManager;

    @Mock
    private ProductDetailContract.View productDetailView;

    @Captor
    private ArgumentCaptor<LoadDataCallback<Product>> loadProductCallbackCaptor;

    private ProductDetailPresenter productDetailPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(productDetailView.isActive()).thenReturn(true);
    }

    @Test
    public void createPresenter_setsThePresenterToView() {

        productDetailPresenter = new ProductDetailPresenter(
            PRODUCT.id,
            productManager,
            cartManager,
            productDetailView);

        verify(productDetailView).setPresenter(productDetailPresenter);
    }

    @Test
    public void getProduct_From_product_manager_And_LoadIntoView() {

        productDetailPresenter = new ProductDetailPresenter(
            PRODUCT.id,
            productManager,
            cartManager,
            productDetailView);

        productDetailPresenter.start();

        verify(productManager).getProduct(eq(PRODUCT.id),
            loadProductCallbackCaptor.capture());

        InOrder inOrder = inOrder(productDetailView);
        inOrder.verify(productDetailView).setLoadingIndicator(true);
        loadProductCallbackCaptor.getValue().onDataLoaded(PRODUCT);

        inOrder.verify(productDetailView).setLoadingIndicator(false);
        verify(productDetailView).showProduct(PRODUCT);
    }

    @Test
    public void add_product_to_cart() {

        Product addProduct = new Product(1, "Name1", "Category1", 10.00, "image_url1", "Description1", false);
        productDetailPresenter = new ProductDetailPresenter(
            addProduct.id,
            productManager,
            cartManager,
            productDetailView);
        productDetailPresenter.start();

        productDetailPresenter.addToCart(addProduct);

        verify(cartManager).addProductToCart(addProduct);
        verify(productDetailView).showProductAddedToCart();
    }
}