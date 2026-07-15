from PIL import Image, ImageDraw

size = 256
img = Image.new('RGBA', (size, size), (0, 0, 0, 255))
draw = ImageDraw.Draw(img)

# Premium Platinum Gradient colors (approximate with flat color for simple script)
# We will draw a Z similar to the SVG
points = [
    (60, 60), (196, 60), (196, 90), 
    (90, 196), (196, 196), (196, 226), 
    (60, 226), (60, 196), (166, 90), 
    (60, 90)
]
draw.polygon(points, fill=(228, 228, 231, 255))

icon_sizes = [(16, 16), (32, 32), (48, 48), (64, 64), (128, 128), (256, 256)]
img.save('public/favicon.ico', format='ICO', sizes=icon_sizes)
img.save('public/zyra-icon.png', format='PNG')
print("Successfully created zyra favicon")
