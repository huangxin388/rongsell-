package com.bupt.core.service.impl;

import com.bupt.core.common.Const;
import com.bupt.core.common.ServerResponse;
import com.bupt.core.dao.CartMapper;
import com.bupt.core.dao.ProductMapper;
import com.bupt.core.entity.Cart;
import com.bupt.core.entity.Product;
import com.bupt.core.service.CartService;
import com.bupt.core.utils.BigDecimalUtil;
import com.bupt.core.utils.PropertyUtil;
import com.bupt.core.vo.CartProductVo;
import com.bupt.core.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author huang xin
 * @Date 2020/3/23 15:17
 * @Version 1.0
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ServerResponse<CartVo> addProduct(Integer userId, Integer productId, Integer count) {
        if(productId == null || count == null) {
            return ServerResponse.getFailureByMessage("参数错误");
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if(cart == null) {
            // 此种商品不在购物车中，需要添加至购物车
            Cart cartItem = new Cart();
            cartItem.setChecked(Const.Cart.PRODUCT_CHECKED);
            cartItem.setCreateTime(new Date());
            cartItem.setProductId(productId);
            cartItem.setQuantity(count);
            cartItem.setUpdateTime(new Date());
            cartItem.setUserId(userId);
            cartMapper.insert(cartItem);
        } else {
            // 商品已在购物车中，只需要更新商品数量
            count = count + cart.getQuantity();
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return getCartInfo(userId);
    }

    @Override
    public ServerResponse<CartVo> updateProduct(Integer userId, Integer productId, Integer count) {
        if(productId == null || count == null) {
            return ServerResponse.getFailureByMessage("参数错误");
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId, productId);
        if(cart != null) {
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKeySelective(cart);
        return getCartInfo(userId);
    }

    @Override
    public ServerResponse<CartVo> deleteProduct(Integer userId, String productIds) {
        if(productIds == null || "".equals(productIds)) {
            return ServerResponse.getFailureByMessage("参数错误");
        }
        List<String> idList = Arrays.asList(productIds.split(","));
        cartMapper.deleteByUserIdProductIds(userId, idList);
        return getCartInfo(userId);
    }

    @Override
    public ServerResponse<CartVo> getCartInfo(Integer userId) {
        CartVo cartVo = getCartVoLimit(userId);
        return ServerResponse.getSuccess(cartVo);
    }

    @Override
    public ServerResponse<CartVo> selectCheckedOrUnCheckedAll(Integer userId, Boolean check) {
        cartMapper.checkedOrUnCheckedProduct(userId, check);
        return getCartInfo(userId);
    }


    @Override
    public ServerResponse<CartVo> setProductCheckStatus(Integer userId, Integer productId, Boolean check) {
        cartMapper.setProductCheckStatus(userId, productId, check);
        return getCartInfo(userId);
    }

    @Override
    public ServerResponse<Integer> getCartProductCount(Integer userId) {
        if(userId == null) {
            return ServerResponse.getSuccess(0);
        }
        return ServerResponse.getSuccess(cartMapper.selectCartProductCount(userId));
    }
    /**
     * 判断购物车中的商品数量是否大于改商品的库存数量，并拼装购物车信息
     * @param userId
     * @return
     */
    private CartVo getCartVoLimit(Integer userId) {
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        BigDecimal cartTotalPrice = new BigDecimal("0");

        if(cartList.size() > 0) {
            for(Cart cartItem : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cartItem.getId());
                cartProductVo.setProductId(cartItem.getProductId());
                cartProductVo.setUserId(cartItem.getUserId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if(product != null) {
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubTitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());
                    // 判断库存
                    int buyLimitCount = 0;
                    if(product.getStock() >= cartItem.getQuantity()) {
                        // 库存充足的时候
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        // 购物车中更新有效数量
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    // 计算总价
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cartItem.getChecked());
                }
                if(cartItem.getChecked() == Const.Cart.PRODUCT_CHECKED) {
                    // 如果商品已经勾选，增加到整个的购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(getAllCheckStatus(userId));
        cartVo.setImageHost(PropertyUtil.getProperty("ftp.server.http.prefix"));
    return cartVo;
    }

    private boolean getAllCheckStatus(Integer userId) {
        if(userId == null) {
            return false;
        }
        return cartMapper.selectCartProductCheckStatusByUserId(userId) == 0;
    }
}
