package com.github.czyzby.lml.parser.impl.attribute.image.animated;

import com.github.czyzby.lml.parser.LmlParser;
import com.github.czyzby.lml.parser.tag.LmlAttribute;
import com.github.czyzby.lml.parser.tag.LmlTag;
import com.github.czyzby.lml.scene2d.ui.reflected.AnimatedImage;

/** See {@link AnimatedImage#setMaxDelay(float)}. Mapped to "maxDelay".
 *
 * @author MJ */
public class AnimationMaxDelayLmlAttribute implements LmlAttribute<AnimatedImage> {
    @Override
    public Class<AnimatedImage> getHandledType() {
        return AnimatedImage.class;
    }

    @Override
    public void process(final LmlParser parser, final LmlTag tag, final AnimatedImage actor,
            final String rawAttributeData) {
        actor.setMaxDelay(parser.parseFloat(rawAttributeData, actor));
    }
}
