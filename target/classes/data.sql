-- Insert sample shoe products
INSERT INTO products (name, brand, price, description, image_url, category, featured, created_at, updated_at) VALUES
('Air Max 270', 'Nike', 150.00, 'Nike''s first lifestyle Air Max brings you style, comfort and big attitude.', 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=500', 'Running', true, NOW(), NOW()),
('Ultra Boost 22', 'Adidas', 180.00, 'More boost, more comfort. The Ultra Boost 22 provides endless energy return.', 'https://images.unsplash.com/photo-1608231387042-66d1773070a5?w=500', 'Running', true, NOW(), NOW()),
('Classic Leather', 'Reebok', 75.00, 'The timeless Classic Leather sneaker that started it all.', 'https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=500', 'Casual', false, NOW(), NOW()),
('Chuck Taylor All Star', 'Converse', 60.00, 'The iconic sneaker that''s been an American original since 1917.', 'https://images.unsplash.com/photo-1607522370275-f14206abe5d3?w=500', 'Casual', true, NOW(), NOW()),
('574 Core', 'New Balance', 85.00, 'A classic New Balance silhouette that never goes out of style.', 'https://images.unsplash.com/photo-1539185441755-769473a23570?w=500', 'Casual', false, NOW(), NOW()),
('Suede Classic', 'Puma', 70.00, 'The legendary Puma Suede has been worn by icons of culture.', 'https://images.unsplash.com/photo-1584735175315-9d5df23860e6?w=500', 'Casual', false, NOW(), NOW()),
('Gel-Kayano 29', 'Asics', 160.00, 'Premium stability and comfort for long-distance running.', 'https://images.unsplash.com/photo-1556906781-9a412961c28c?w=500', 'Running', true, NOW(), NOW()),
('Fresh Foam 1080', 'New Balance', 150.00, 'Plush cushioning for a smooth, comfortable ride.', 'https://images.unsplash.com/photo-1460353581641-37baddab0fa2?w=500', 'Running', false, NOW(), NOW()),
('Metcon 8', 'Nike', 140.00, 'Built for your toughest workouts with stability and durability.', 'https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=500', 'Training', false, NOW(), NOW()),
('NMD_R1', 'Adidas', 130.00, 'Modern street style with responsive Boost cushioning.', 'https://images.unsplash.com/photo-1552346154-21d32810aba3?w=500', 'Casual', true, NOW(), NOW()),
('Jordan 1 Retro High', 'Nike', 170.00, 'The iconic Jordan 1 with classic colorways and premium leather.', 'https://images.unsplash.com/photo-1511556532299-8f662fc26c06?w=500', 'Basketball', true, NOW(), NOW()),
('Speedcross 5', 'Salomon', 140.00, 'Aggressive grip for technical trails and varying conditions.', 'https://images.unsplash.com/photo-1551107696-a4b0c5a0d9a2?w=500', 'Trail', false, NOW(), NOW());

-- Insert product variants for each product
-- Air Max 270 (Product ID 1)
INSERT INTO product_variants (size, color, stock, product_id) VALUES
(7.0, 'Black', 15, 1),
(7.5, 'Black', 20, 1),
(8.0, 'Black', 18, 1),
(8.5, 'Black', 22, 1),
(9.0, 'Black', 25, 1),
(9.5, 'Black', 20, 1),
(10.0, 'Black', 15, 1),
(7.0, 'White', 12, 1),
(8.0, 'White', 16, 1),
(9.0, 'White', 20, 1),
(10.0, 'White', 14, 1);

-- Ultra Boost 22 (Product ID 2)
INSERT INTO product_variants (size, color, stock, product_id) VALUES
(7.0, 'Core Black', 18, 2),
(8.0, 'Core Black', 22, 2),
(9.0, 'Core Black', 25, 2),
(10.0, 'Core Black', 20, 2),
(7.0, 'Cloud White', 15, 2),
(8.0, 'Cloud White', 18, 2),
(9.0, 'Cloud White', 22, 2),
(10.0, 'Cloud White', 16, 2);

-- Classic Leather (Product ID 3)
INSERT INTO product_variants (size, color, stock, product_id) VALUES
(7.0, 'White', 30, 3),
(8.0, 'White', 35, 3),
(9.0, 'White', 40, 3),
(10.0, 'White', 35, 3),
(7.0, 'Black', 25, 3),
(8.0, 'Black', 30, 3),
(9.0, 'Black', 35, 3),
(10.0, 'Black', 30, 3);

-- Chuck Taylor All Star (Product ID 4)
INSERT INTO product_variants (size, color, stock, product_id) VALUES
(7.0, 'Black', 45, 4),
(8.0, 'Black', 50, 4),
(9.0, 'Black', 55, 4),
(10.0, 'Black', 50, 4),
(7.0, 'White', 40, 4),
(8.0, 'White', 45, 4),
(9.0, 'White', 50, 4),
(10.0, 'White', 45, 4),
(8.0, 'Red', 30, 4),
(9.0, 'Red', 35, 4);

-- 574 Core (Product ID 5)
INSERT INTO product_variants (size, color, stock, product_id) VALUES
(7.0, 'Grey', 28, 5),
(8.0, 'Grey', 32, 5),
(9.0, 'Grey', 35, 5),
(10.0, 'Grey', 30, 5),
(8.0, 'Navy', 25, 5),
(9.0, 'Navy', 28, 5),
(10.0, 'Navy', 25, 5);

-- Suede Classic (Product ID 6)
INSERT INTO product_variants (size, color, stock, product_id) VALUES
(7.0, 'Peacoat', 22, 6),
(8.0, 'Peacoat', 26, 6),
(9.0, 'Peacoat', 30, 6),
(10.0, 'Peacoat', 26, 6),
(8.0, 'High Risk Red', 20, 6),
(9.0, 'High Risk Red', 24, 6);

-- Gel-Kayano 29 (Product ID 7)
INSERT INTO product_variants (size, color, stock, product_id) VALUES
(7.0, 'Black/White', 16, 7),
(8.0, 'Black/White', 20, 7),
(9.0, 'Black/White', 24, 7),
(10.0, 'Black/White', 20, 7),
(8.0, 'French Blue', 15, 7),
(9.0, 'French Blue', 18, 7);

-- Fresh Foam 1080 (Product ID 8)
INSERT INTO product_variants (size, color, stock, product_id) VALUES
(7.0, 'Black', 18, 8),
(8.0, 'Black', 22, 8),
(9.0, 'Black', 26, 8),
(10.0, 'Black', 22, 8),
(8.0, 'White', 16, 8),
(9.0, 'White', 20, 8);

-- Metcon 8 (Product ID 9)
INSERT INTO product_variants (size, color, stock, product_id) VALUES
(7.0, 'Black/Volt', 14, 9),
(8.0, 'Black/Volt', 18, 9),
(9.0, 'Black/Volt', 22, 9),
(10.0, 'Black/Volt', 18, 9),
(8.0, 'White/Crimson', 12, 9),
(9.0, 'White/Crimson', 16, 9);

-- NMD_R1 (Product ID 10)
INSERT INTO product_variants (size, color, stock, product_id) VALUES
(7.0, 'Core Black', 20, 10),
(8.0, 'Core Black', 24, 10),
(9.0, 'Core Black', 28, 10),
(10.0, 'Core Black', 24, 10),
(8.0, 'Cloud White', 18, 10),
(9.0, 'Cloud White', 22, 10);

-- Jordan 1 Retro High (Product ID 11)
INSERT INTO product_variants (size, color, stock, product_id) VALUES
(7.0, 'Chicago', 10, 11),
(8.0, 'Chicago', 12, 11),
(9.0, 'Chicago', 15, 11),
(10.0, 'Chicago', 12, 11),
(8.0, 'Bred', 8, 11),
(9.0, 'Bred', 10, 11),
(8.0, 'Royal Blue', 9, 11),
(9.0, 'Royal Blue', 11, 11);

-- Speedcross 5 (Product ID 12)
INSERT INTO product_variants (size, color, stock, product_id) VALUES
(7.0, 'Black', 16, 12),
(8.0, 'Black', 20, 12),
(9.0, 'Black', 24, 12),
(10.0, 'Black', 20, 12),
(8.0, 'Navy Blazer', 14, 12),
(9.0, 'Navy Blazer', 18, 12);

-- Create default admin user (password: admin123)
INSERT INTO users (id, name, email, password, role, created_at, updated_at) VALUES
(UNHEX(REPLACE(UUID(), '-', '')), 'Admin User', 'admin@solestride.com', '$2a$10$XptfskLsT1l/bRTLRiiCgejHqOpgXFreUnNUa35gJdCr2v2QbVFzu', 'ADMIN', NOW(), NOW());

-- Create sample customer user (password: customer123)
INSERT INTO users (id, name, email, password, role, created_at, updated_at) VALUES
(UNHEX(REPLACE(UUID(), '-', '')), 'John Doe', 'customer@solestride.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'CUSTOMER', NOW(), NOW());