package com.mmall.service.impl;

import com.mmall.common.Constant;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iCartService")
public class CartServiceImpl {

    @Autowired
    private CartMapper cartMapper;

    public ServerResponse add(Integer userId,Integer productId,Integer count) {
        Cart cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        if (cart == null) {
            //如果这个产品不在这个购物车里，需要新增一个这个产品的记录
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Constant.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);

            cartMapper.insert(cartItem);
        } else {
            //如果产品已存在，数量相加
            count = cart.getQuantity() + count ;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);
        }
        return null;
    }
}
