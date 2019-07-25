package com.alfredthomas.tripeaks.UI;

import com.alfredthomas.tripeaks.card.Card;

import java.lang.ref.WeakReference;

public abstract class DiscardListener {
    WeakReference<PlayingCardView> discardView;
            public Card discardChanged()
            {
                return discardView.get().card;
            }
}
