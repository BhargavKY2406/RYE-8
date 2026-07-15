-- ================================================================
-- Zyra — Seed Data for restaurant_db (Updated Absolute Links)
-- ================================================================

-- ---- Restaurants ----
INSERT INTO Restaurant (Name, CuisineType, DeliveryTime, Address, Rating, IsActive, ImagePath, IsTopRestaurant) VALUES
('The Saffron Estate',      'Indian',    30, '123 100ft Road, Indiranagar, Bangalore',      4.5, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRERBFxRTAbDzVZlXEuzzObsjVR3_T0IwguG6Qr5v0ANA&s=10', true),
('L''Antica Forno',    'Italian',   25, '45 80ft Road, Koramangala, Bangalore',        4.3, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpVin-3ug64d4d-fudjhbv-gMrMvA06PzwWm1RQHPOvg&s=10', true),
('The Imperial Wok',        'Chinese',   35, '78 11th Main, Jayanagar, Bangalore',          4.1, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRoPkN5eu2wBgFfCvcRxf3o3q3DTAGsu7RLAKIfiKPYXA&s=10', true),
('Casa del Sol Azul',       'Mexican',   20, '12 Whitefield Main Road, Bangalore',          4.6, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMHQTIo40a9yPjDFRH_ppfV2_d3wRS98jbErv1rPalDA&s=10', false),
('Nobu Ateliers',      'Japanese',  40, '90 HSR Layout Sector 1, Bangalore',           4.7, true, 'https://kamereo.vn/blog/wp-content/uploads/2025/01/restaurant-design-1.jpg', false),
('The Reserve Grill',       'American',  15, '56 MG Road, Bangalore',                       4.2, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMjoZkU0A_Vpq2nqVy-O5mZdf4MuK5QU3qe1k4njvnqw&s=10', false),
('Maharaja''s Court',       'Indian',    30, '34 Brigade Road, Bangalore',                  4.4, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSY9EWRkJtAXpgNDa2qDhKMGFPyKMmkp-EgBnR4AnX1GQ&s=10', false),
('Il Tartufo',      'Italian',   28, '67 Sampige Road, Malleshwaram, Bangalore',    4.0, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRk9-1vbRI3jX9JVxUr0-_yeGQUZzcUfVZI7iBK2qhWlA&s=10', false),
('Aura Botanica',    'Vegan',     35, '101 JP Nagar 2nd Phase, Bangalore',           4.8, true, 'https://b.zmtcdn.com/data/pictures/6/18353106/20c64ea1876e542128db2f4b43a3f41b_featured_v3.jpg', false),
('L''Éclair de Génie',     'Dessert',   20, '15 Electronic City Phase 1, Bangalore',       4.9, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQF4QjFXAvvYPHqclUtn6gG--OkK0RUFcrphcOvPxI0MQ&s=10', false),
('L''Ambroisie',     'French',    45, '55 BTM Layout 1st Stage, Bangalore',          4.6, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRCP1To31U34DVEheW-zymaf-q4H4OEkXH0wBB1kQ2Mew&s=10', false),
('The Oak Room Grill',  'American',  25, '100 Marathahalli ORR, Bangalore',             4.9, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1hksaaeDgWiUHNoV8pXH3TXxgGAj3BJvnsrcu7U8VxA&s=10', false),
('Oceanic Eminence',  'Seafood',   45, '200 Bellandur Main Road, Bangalore',          4.8, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTF8wtX6HTBINJwv23-EI9_dYgSAJ94KDN0KSKEaZmrzA&s=10', false),
('Café de l''Opéra',     'Cafe',      15, '50 Sarjapur Road, Bangalore',                 4.7, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTjWx60I9OGPi5dWdLwcialxCrPc5dedvGEh88X9o0vkg&s=10', false),
('Darbar Royal',   'Indian',    40, '400 Bannerghatta Road, Bangalore',            4.8, true, 'https://assets.architecturaldigest.in/photos/661914a9f7fde7d31839ec01/16:9/w_1280,c_limit/Untitled%20design%20(4).jpg', false),
('The Wellness Estate',    'Healthy',   20, '202 Richmond Road, Bangalore',                4.5, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTKo9Tbn-2JPA71wG13rlPQR6pD0BHgn8yN5t_ctXXyBg&s=10', false);

-- ---- Menu Items: Spice Garden (RestaurantID = 1) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(1, 'Smoked Heirloom Butter Chicken',     'Creamy tomato-based chicken curry with aromatic spices',       1200.00, true, 'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%2Fid%2FOIP.jmQclDRxfydeD1uulIH8HAHaEJ%3Fr%3D0%26pid%3DApi&f=1&ipt=1eecf5ac8861bf510ef59c9e9ccdb90927602b1e7b2d682ec79b65562f28c787&ipo=images', true),
(1, 'Charcoal-Fired Artisanal Paneer Tikka',       'Grilled cottage cheese marinated in tandoori spices',          975.00, true, 'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fthf.bing.com%2Fth%2Fid%2FOIP.BMHvuDgIZU6bwMmH9AhNGAHaE7%3Fr%3D0%26cb%3Dthfc1falcon4%26pid%3DApi&f=1&ipt=4d91946e2ceb1a5cf3b85079d1e7d6b975cca963b191832b27d0042e2735efc0&ipo=images', true),
(1, 'Dum-Pukht Saffron Chicken Biryani',  'Fragrant basmati rice layered with spiced chicken',            1050.00, true, 'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fthf.bing.com%2Fth%2Fid%2FOIP.3wVVsNFt-0IbvMJ6AAI_NwHaE8%3Fr%3D0%26cb%3Dthfc1falcon4%26pid%3DApi&f=1&ipt=a2406a4b8cce1f52a22931ffbf5e9e42474e00290b909c1a77a8f9a630278de3&ipo=images', true),
(1, '24-Hour Slow-Cooked Dal Makhani',        'Slow-cooked black lentils in a rich buttery gravy',            750.00, true, 'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%2Fid%2FOIP.vVODeHkAnEyi-Ydn_W5PnQHaEK%3Fr%3D0%26pid%3DApi&f=1&ipt=a707fcbb9bebbf56efb396dad64a81bf5355ff413c89bdabc54349f3a325c4e3&ipo=images', true),
(1, 'Wood-Fired Garlic and Truffle Butter Naan',        'Soft leavened bread topped with fresh garlic and butter',       225.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTOVSZRPJPCA0ETEHty3aG4gQBf6EEEyXuI0YX5Q-Sh1w&s=10', true),
(1, 'Rose-Scented Milk Gulab Jamun',        'Soft milk-solid dumplings soaked in rose-flavored syrup',      450.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSj-rTVr8WvgL4TyemiwIWdf0MR1sLkaICJfUTKZ8_mLQ&s=10', false);

-- ---- Menu Items: Pizza Paradise (RestaurantID = 2) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(2, 'San Marzano & Buffalo Margherita Pizza',   'Classic pizza with fresh mozzarella, tomato sauce, and basil', 1312.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRnkFeRpFLk9FdejB2j1UJYFTkxuQH9E6fLt9QBtI5nSA&s=10', false),
(2, 'Wood-Fired Wagyu Pepperoni Pizza',    'Loaded with pepperoni slices and gooey mozzarella cheese',     1575.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSoLg0XGaH0pftJ4LfOBIOOYEWWXTvG838q_0rFv10N8g&s=10', false),
(2, 'Confit Garlic and Rosemary Bread',       'Toasted bread with garlic butter and herbs',                   562.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS76YqppHkB7im6KdlKrC1leQQL5PJVCdQnzbshbzdp5g&s=10', false),
(2, 'White Truffle Pasta Alfredo',      'Creamy Alfredo sauce tossed with fettuccine pasta',            1125.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgcgodLZq3BPfERxg4HG7KXzCMPXU_FwCz3qc6PYqBRw&s=10', false),
(2, 'Espresso-Soaked Mascarpone Tiramisu',           'Classic Italian dessert with coffee-soaked ladyfingers',        825.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRC0HB3F8R1PXToum11kugVZmi833xLBC7Ca8vSGgt-qQ&s=10', false);

-- ---- Menu Items: Dragon Wok (RestaurantID = 3) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(3, 'Aged-Soy Szechuan Kung Pao Chicken',   'Spicy stir-fried chicken with peanuts and vegetables',          1162.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSsxRA928RjNsP3uR4E9vJO0pejkxo7KHMpoKaaX-r2ZQ&s=10', false),
(3, 'Crispy Lotus Root Veg Manchurian',     'Deep-fried veggie balls in a tangy Indo-Chinese sauce',        900.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzvYkCbHyFjRjTJy5CcPpiDrga0Pq3sqVzIo3zHnf5cA&s=10', false),
(3, 'Hand-Pulled Imperial Hakka Noodles',      'Stir-fried noodles with fresh vegetables and soy sauce',       750.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcReUD8uK1haMzlVPq5yEG4jUGQzXFYMFsgpgxRAuDehtA&s=10', false),
(3, 'Golden Lotus Spring Rolls',       'Crispy rolls filled with mixed vegetables',                    675.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSKnR3JVYxMShhnP7UAquXlHeIugWzkMAA5minjEe4FIQ&s=10', false),
(3, 'Steamed Crystal Dim Sum Platter',    'Assorted steamed dumplings with dipping sauce',                1312.50, true, 'https://media.istockphoto.com/id/1309978297/photo/asian-food-gyoza-or-jiaozi-fried-dumplings.jpg?s=612x612&w=0&k=20&c=GPlqOty9KHl8P7ekII21PO-EbtIvgbPNn15cBaDbxm0=', false);

-- ---- Menu Items: Taco Fiesta (RestaurantID = 4) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(4, 'Adobo-Braised Chicken Tacos',      'Soft tortillas with seasoned chicken, salsa, and guacamole',   1050.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJh33rxI798Fgxy4gF-XPkQzB9lXrOGLolG3NssMfpeQ&s=10', false),
(4, 'Slow-Roasted Barbacoa Beef Burrito',       'Large flour tortilla stuffed with beef, rice, and beans',       1312.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRG7RzrWnbyT9Qxztax5Kalk_x-KmXLmYHiE-jAb0ZV6Q&s=10', false),
(4, 'Blue Corn Artisanal Nachos Supreme',     'Crispy tortilla chips with cheese, jalapeños, and sour cream', 825.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ9iwemIxFbzNUvYVKumHcRpbb_wLFwdOE5pXK5nnTZIA&s=10', false),
(4, 'Cinnamon-Dusted Churros with Dulce de Leche',            'Fried dough sticks dusted with cinnamon sugar',                 562.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSI_OVgBBpbeREvWbB7gKsSdaELa2g_j6wdjy1Od8YXag&s=10', false);

-- ---- Menu Items: Sakura Sushi (RestaurantID = 5) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(5, 'Ora King Salmon Nigiri with Caviar',      'Fresh salmon slices on seasoned sushi rice',                    1500.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRNMwjxLInPQcJM6lP3nM3sO3LfwLKScQ7bt62lwow_sg&s=10', false),
(5, 'Alaskan King Crab California Roll',    'Crab, avocado, and cucumber inside-out roll',                  1312.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAMrXvWTBJp2g4swXQUI4FCtZpOmQ3crNkzxvMcjD10A&s=10', false),
(5, 'Tonkotsu Ramen with Kurobuta Pork Belly',         'Rich pork broth with noodles, egg, and chashu pork',           1425.00, true, 'https://thumbs.dreamstime.com/b/top-down-view-delicious-bowl-traditional-japanese-tonkotsu-ramen-hot-noodle-soup-features-rich-pork-bone-broth-chashu-396697388.jpg', false),
(5, 'Hokkaido Scallop Tempura Platter',    'Lightly battered and fried shrimp and vegetables',              1200.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6uZ0YRYw15Hq5Rnzcl6TZb4vrL-CENj55ndikn0P-uQ&s=10', false),
(5, 'Matcha Green Tea Mochi Ice Cream',    'Japanese rice cake filled with ice cream',                      675.00, true, 'https://static.ffx.io/images/$zoom_0.27399741267787836%2C$multiply_0.7025%2C$ratio_1.777778%2C$width_1059%2C$x_0%2C$y_48/t_crop_custom/q_86%2Cf_auto/57624f7bbf2a54f360c5669ffdd18893c46cac0d', false);

-- ---- Menu Items: Burger Barn (RestaurantID = 6) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(6, 'A5 Wagyu Classic Cheeseburger','Juicy beef patty with cheddar, lettuce, tomato, and pickles', 937.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQz8GIm8sw9DhRVroCRclPlRyv2jlg0BLVbDDWL_20rig&s=10', false),
(6, 'Dry-Aged Beef & Smoked Pancetta Burger',   'Beef patty with crispy bacon, BBQ sauce, and onion rings',     1200.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ0HDh4yTQJSAd1laJZJ7VZDRFcwyt1tExqdVW-XL1RzQ&s=10', false),
(6, 'Buttermilk Confit Crispy Chicken Burger','Fried chicken breast with coleslaw and mayo',                1050.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSnyCWUyJHm8WZX5xTok0bLLrxVZep_y1Uz8XPKiI_vdA&s=10', false),
(6, 'Hand-Cut Truffle & Parmesan French Fries',       'Golden crispy fries with seasoning salt',                       450.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZIW5JC96xl4B6Wc4Bd2SLjFvh00N195GFDdqwrff9fg&s=10', false),
(6, 'Madagascar Vanilla Bean Milkshake',          'Thick and creamy vanilla milkshake',                            600.00, true, 'https://www.unicornsinthekitchen.com/wp-content/uploads/2018/07/Vanilla-Milkshake-square.jpg', false);

-- ---- Menu Items: Curry House (RestaurantID = 7) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(7, 'Free-Range Chicken Chettinad',  'Fiery South Indian chicken curry with roasted spices',         1275.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRY4OiTB3zc7Xa5gGNR_S-tAVuHgIDp05SRg2JGc-sNpg&s=10', false),
(7, 'Gold-Dusted Crispy Masala Dosa',        'Crispy crepe stuffed with spiced potato filling',               562.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRqY_n30eWX_qP1H22wNYu3M4-TtnVrOcJsGU-u2KOHyg&s=10', false),
(7, 'Slow-Braised Kashmiri Lamb Rogan Josh',    'Tender lamb cooked in aromatic Kashmiri spices',                1425.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRytcP6Dms5d7B_CBvyFV_B6r2xMmxCnOn4b9t9P-CzeA&s', false),
(7, 'Saffron-Infused Ricotta Rasmalai',           'Soft paneer discs in sweetened, cardamom-flavored milk',        525.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS8l0AaGLj2DZpsagWOf31zlTfT_p1Fi2rpzTUgvtL8AA&s=10', false);

-- ---- Menu Items: Pasta Palace (RestaurantID = 8) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(8, 'Wild Boar Ragu Spaghetti Bolognese','Classic Italian meat sauce over spaghetti pasta',              1125.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRsaZkMHEGVgRHQJK5MiP1i8QyolC_VmkwfcaaNAidq6w&s=10', false),
(8, 'Fire-Roasted Pomodoro Penne Arrabbiata',   'Penne pasta in a spicy tomato sauce with garlic',              975.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSxZDKqh7RQjk9Pb-5cW9VKgyKKcrU1BzmNEbqy9x5vBg&s=10', false),
(8, 'Heirloom Romaine Caesar Salad',       'Romaine lettuce, croutons, parmesan, and Caesar dressing',     750.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQne-cJJ41alfSQVgT0fl_ZiIjZWznDqnYk3xjekfYBQ&s=10', false),
(8, 'San Marzano Tomato & Burrata Bruschetta',         'Toasted bread topped with fresh tomatoes, basil, and garlic',  675.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT4UFgdzu0nds2pzZvhgGkq6CZQ1f6h4swABXgxmHFZ1Q&s=10', false),
(8, 'Vanilla Bean Panna Cotta with Caviar De Cassis',        'Creamy Italian dessert with vanilla and berry compote',         712.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTx0BIMTNGpUda2yozq53HszG58kVB1_RjKB3VuPGZwuQ&s=10', false);

-- ---- Menu Items: The Green Leaf (RestaurantID = 9) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(9, 'Plant-Based Artisanal Vegan Burger',       'Plant-based patty with vegan cheese and fresh greens',         1312.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS-NxRm35n9fVABV1ULx3MlsRAjSnMbncoPyuuun5HLZg&s=10', false),
(9, 'Organic Quinoa & Microgreens Salad',       'Healthy quinoa mixed with avocado, cherry tomatoes, and lime', 1050.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRPlgO_i0B3QF83wsYSO9pdY64Kh8mIF-gyG0dkZo_tPQ&s=10', false),
(9, 'Cold-Pressed Detox Green Smoothie',     'Refreshing blend of spinach, green apple, and ginger',         562.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS6UmH4V-481U-q7MnGtmBAZIax6iXAyhQfISOrJsPKIQ&s=10', false),
(9, 'Baked Heritage Sweet Potato Fries', 'Baked sweet potato sticks with a hint of paprika',             675.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQih1JETkpIBj6DpU2eUMI7jDLH6Tut7c_eUjlWZmVjdA&s=10', false);

-- ---- Menu Items: Waffle Wonder (RestaurantID = 10) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(10, 'Belgian Artisanal Classic Waffle',    'Golden Belgian waffle served with maple syrup and butter',     750.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRNzxV7tTetoF8Il6yqe_t1eAO4UXGCst-C2iIl9wdYQA&s=10', false),
(10, 'Hazelnut Praline & Valrhona Chocolate Waffle',   'Waffle loaded with Nutella spread and sliced bananas',         1050.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRKC5xhy4sJzjwy933hUYViDElpb2xSCAv3ob_5E6cUjg&s=10', false),
(10, 'Nitro Cold Brew Iced Coffee',       'Thick cold coffee blended with vanilla ice cream',             600.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPmaZ2RsV4CgDdSAQsxpP8-iw66cnrM4USNq2pUlUVyg&s=10', false),
(10, 'Ricotta Pancakes with Wild Berry Compote',    'Fluffy pancakes topped with mixed berry compote',              937.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEL-Tpd--22iYc66NtrXfJk6MpfszjRZIZEyngb-rAQg&s=10', false);

-- ---- Menu Items: Bistro French (RestaurantID = 11) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(11, 'Caramelized Heirloom French Onion Soup', 'Rich beef broth with caramelized onions and gruyere cheese',   1200.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTtTWFyRPrOcX7jRc-DdKvJw_FQgsx3QNJFQKJAnuq_jA&s=10', false),
(11, 'Burgundy Braised Coq au Vin',        'Chicken braised with wine, mushrooms, and pearl onions',       1687.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSk2Vzqb-_NviiK-2mUVql7USvH5Fx573Hp4_OdPo9sXw&s=10', false),
(11, 'Cultured Butter Artisanal Croissant',         'Buttery and flaky classic French pastry',                      562.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmqsv4Ock0cSj5WU5cN7k-kGHgFLnE7ajVH9RWO6Q75A&s=10', false),
(11, 'Madagascar Vanilla Crème Brûlée',      'Rich custard base topped with a layer of hardened caramelized sugar', 1050.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWFdmm4rg9NrClix0UKYnu7JSlfAIurggMgT81PR5V3g&s=10', false);

-- ---- Menu Items: The Rustic Grill (RestaurantID = 12) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(12, 'Double A5 Wagyu Truffle Smashburger', 'Double smashburger with melted cheese and truffle sauce',       1687.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxqH0Ijfe9QI_uMuvw8k9VzmP5LAmyn2jC0czcOHsVHg&s=10', false),
(12, 'Gruyere & Black Truffle Loaded Fries','Crispy golden french fries loaded with melted cheese and herbs', 937.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTMCK8UqgdZzoeqFAMGHEStXyGTrnG9ehfzPdCWIoRnlQ&s=10', false);

-- ---- Menu Items: Seafood Symphony (RestaurantID = 13) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(13, 'Butter-Poached Maine Lobster Tail','Perfectly grilled lobster tail dripping in garlic butter',      2812.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQuCIGvJFdk6W0cto6lK29d7lkqIuI7BAUbPmKdzapnCA&s=10', false),
(13, 'Pan-Seared Hokkaido Garlic Scallops',     'Pan-seared scallops in a garlic butter herb sauce',             2062.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_-y9JUZcDGTyQktfmc2DgcqRIi9kLpoHqwbTbv1IU-A&s=10', false);

-- ---- Menu Items: Coffee Corner (RestaurantID = 14) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(14, 'Single-Origin Ethiopian Cappuccino',          'Classic Italian coffee with steamed milk foam',                 562.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAj8YKdkau2zG8W3BVFDQSBtb-iP6QuqdZiBWI0pBqzg&s=10', false),
(14, 'Wild Blueberry & Lemon Zest Muffin',    'Freshly baked muffin bursting with blueberries',                450.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQoBPtzbL5yP39MZXo6NgTyh0R0s7DTinIjyxSi8SdYQ&s=10', false);

-- ---- Menu Items: Mughlai Majesty (RestaurantID = 15) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(15, 'Awadhi Royal Mutton Biryani',      'Rich, flavorful basmati rice cooked with tender mutton',        1687.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRrArQ6BYB1h6eeXP7uSnjz9nbjhVRxkB1xbMOzL_iAbw&s', false),
(15, 'Pistachio & Cashew Crusted Chicken Korma',       'Mild, creamy chicken curry with nuts and yogurt',               1312.50, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5SugIWaMGEKH_cSgi0wheC7Z_11GbGJzz99e4p0m9_g&s=10', false),
(15, 'Charcoal-Smoked Lamb Seekh Kebab',         'Minced meat spiced and grilled on skewers',                     1050.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCxcVfY-Lap6Xt8l2qzJqHy3If2lnsmfzDkACk_uP8_w&s=10', false);

-- ---- Menu Items: Healthy Habits (RestaurantID = 16) ----
INSERT INTO Menu (RestaurantID, ItemName, Description, Price, IsAvailable, ImagePath, IsBestDish) VALUES
(16, 'Hass Avocado on Sourdough Toast',       'Smashed avocado on whole grain bread with cherry tomatoes',     825.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS51yhmqJF5LEhafEtW9WSLrbvfoPMROH2V4GBXP5R2HA&s=10', false),
(16, 'Heritage Grains Farm Egg Protein Bowl',        'Grilled chicken, quinoa, spinach, and roasted chickpeas',       1200.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRap_wj_1AM4thsbqPvsiZX5ZTQls8Bd0gC6lwCFgQK5g&s=10', false),
(16, 'Amazonian Acai Bowl with Wild Berries',           'Blended acai berries topped with granola and fresh fruit',      975.00, true, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRycY-wiJh7F0tjID6P2zFMsSvwDACR3Jg7QefOPV_Gog&s=10', false);
