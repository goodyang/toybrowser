package com.goodyang.toybrowser.layout;

import com.goodyang.toybrowser.css.Keyword;
import com.goodyang.toybrowser.css.Length;
import com.goodyang.toybrowser.css.Unit;
import com.goodyang.toybrowser.css.Value;
import com.goodyang.toybrowser.style.StyleNode;
import com.goodyang.toybrowser.util.Utils;

import java.security.Key;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/6.
 */
public class LayoutBox {
    public Dimensions dimensions;
    public BoxType boxType;
    public ArrayList<LayoutBox> children;

    public LayoutBox(BoxType boxType) {
        this.boxType = boxType;
        this.dimensions = new Dimensions();
        this.children = new ArrayList<>();
    }

    public StyleNode getStyleNode (){
        if(boxType instanceof BlockNode) {
            return ((BlockNode) boxType).styleNode;
        } else if(boxType instanceof InlineNode) {
            return ((InlineNode) boxType).styleNode;
        } else {
            throw new RuntimeException("anonymous block box has no style node");
        }
    }

    public void layout(Dimensions containingBlock) {
        if(boxType instanceof BlockNode) {
            layout_block(containingBlock);
        }
    }

    private void layout_block(Dimensions containingBlock) {
        calculate_block_width(containingBlock);

        calculate_block_position(containingBlock);

        layout_block_children();

        calculate_block_height();
    }

    private void calculate_block_width(Dimensions containingBlock) {
        StyleNode styleNode = getStyleNode();

        Keyword auto = new Keyword("auto");
        Value width = styleNode.value("width");
        if(width == null) {
            width = auto;
        }

        Length zero = new Length(0.0f, Unit.Px);

        Value margin_left = styleNode.lookup("margin-left", "margin", zero);
        Value margin_right = styleNode.lookup("margin-right", "margin", zero);

        Value border_left = styleNode.lookup("border-left-width", "border-width", zero);
        Value border_right = styleNode.lookup("border-right-width", "border-width", zero);

        Value padding_left = styleNode.lookup("padding-left", "padding", zero);
        Value padding_right = styleNode.lookup("padding-right", "padding", zero);

        float total = Utils.sumLength(margin_left, margin_right, border_left, border_right,
                padding_left, padding_right, width);

        boolean isAuto_margin_left = margin_left instanceof Keyword
                && ((Keyword) margin_left).getKeyword().equals(auto.getKeyword());
        boolean isAuto_margin_right = margin_right instanceof Keyword
                && ((Keyword) margin_right).getKeyword().equals(auto.getKeyword());
        boolean isAuto_width = width instanceof Keyword
                && ((Keyword) width).getKeyword().equals(auto.getKeyword());

        if(isAuto_width && total > containingBlock.content.width ){
            if(isAuto_margin_left) {
                margin_left = new Length(0.0f, Unit.Px);
            }
            if(isAuto_margin_right) {
                margin_right = new Length(0.0f, Unit.Px);
            }
        }

        float underflow =  containingBlock.content.width - total;

        if(!isAuto_width && !isAuto_margin_left && !isAuto_margin_right) {
            margin_right = new Length(margin_right.to_px()+underflow, Unit.Px);
        }else if(!isAuto_width && !isAuto_margin_left && isAuto_margin_right) {
            margin_right = new Length(underflow, Unit.Px);
        }else if(!isAuto_width && isAuto_margin_left && !isAuto_margin_right) {
            margin_left = new Length(underflow, Unit.Px);
        }else if(isAuto_width){
            if(isAuto_margin_left) {
                margin_left = new Length(0.0f, Unit.Px);
            }
            if(isAuto_margin_right) {
                margin_right = new Length(0.0f, Unit.Px);
            }

            if(underflow >= 0.0) {
                width = new Length(underflow, Unit.Px);
            } else {
                width = new Length(0.0f, Unit.Px);
                margin_right = new Length(margin_right.to_px() + underflow, Unit.Px);
            }
        }else if(!isAuto_width && isAuto_margin_left && isAuto_margin_right) {
            margin_left = new Length(underflow/2.0f, Unit.Px);
            margin_right = new Length(underflow/2.0f, Unit.Px);
        }

        Dimensions d = this.dimensions;
        d.content.width = width.to_px();

        d.padding.left = padding_left.to_px();
        d.padding.right = padding_right.to_px();

        d.border.left = border_left.to_px();
        d.border.right = border_right.to_px();

        d.margin.left = margin_left.to_px();
        d.margin.right = margin_right.to_px();
    }

    private void calculate_block_position(Dimensions containingBlock) {
        StyleNode style = getStyleNode();
        Dimensions d = this.dimensions;

        Length zero  = new Length(0.0f, Unit.Px);

        d.margin.top = style.lookup("margin-top", "margin", zero).to_px();
        d.margin.bottom = style.lookup("margin-bottom", "margin", zero).to_px();

        d.border.top = style.lookup("border-top-width", "border-width", zero).to_px();
        d.border.bottom = style.lookup("border-bottom-width", "border-width", zero).to_px();

        d.padding.top = style.lookup("padding-top", "padding", zero).to_px();
        d.padding.bottom = style.lookup("padding-bottom", "padding", zero).to_px();

        d.content.x = containingBlock.content.x + d.margin.left + d.border.left + d.padding.left;
        d.content.y = containingBlock.content.height + containingBlock.content.y +
                + d.margin.top + d.border.top + d.padding.top;
    }

    private void layout_block_children() {
        Dimensions d = this.dimensions;

        for(LayoutBox child : children) {
            child.layout(d);

            d.content.height = d.content.height + child.dimensions.getMarginBox().height;
        }
    }

    private void calculate_block_height() {
        Value height = getStyleNode().value("height");
        if(height instanceof Length) {
            this.dimensions.content.height = ((Length) height).getLength();
        }
    }

    public LayoutBox get_inline_container() {
        if(boxType instanceof InlineNode || boxType instanceof AnonymousBlock) {
            return this;
        }else if(boxType instanceof BlockNode) {
            LayoutBox lastChild = children.size()==0?null: children.get(children.size()-1);
            if(lastChild!=null && lastChild.boxType instanceof  AnonymousBlock) {
                return lastChild;
            }else{
                children.add(new LayoutBox(new AnonymousBlock()));
                return children.get(children.size() - 1);
            }
        }
        return null;
    }


}
