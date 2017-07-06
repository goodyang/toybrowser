package com.goodyang.toybrowser.layout;

import com.goodyang.toybrowser.style.Display;
import com.goodyang.toybrowser.style.StyleNode;

/**
 * Created by Administrator on 2017/7/6.
 */
public class LayoutBoxBuilder {
    public static LayoutBox getLayoutTree(StyleNode node, Dimensions containingBlock) {
        containingBlock.content.height = 0.0f;

        LayoutBox root_box = buildLayoutTree(node);

        root_box.layout(containingBlock);

        return root_box;
    }

    private static LayoutBox buildLayoutTree(StyleNode styleNode) {
        LayoutBox root = null;

        Display display = styleNode.display();
        if(display == Display.Block) {
            root = new LayoutBox(new BlockNode(styleNode));
        } else if(display == Display.Inline) {
            root = new LayoutBox(new InlineNode(styleNode));
        } else if(display == Display.None){
            throw new RuntimeException("root not has display:none");
        }

        if(root != null) {
            for(StyleNode child : styleNode.children) {
                Display childDis = child.display();
                if(childDis == Display.Block) {
                    root.children.add(buildLayoutTree(child));
                } else if(childDis == Display.Inline){
                    root.get_inline_container().children.add(buildLayoutTree(child));
                }
            }
        }

        return root;
    }
}
