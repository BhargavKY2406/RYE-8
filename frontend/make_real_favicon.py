from PIL import Image

try:
    img = Image.open('public/rye8-icon.png')
    
    # Ensure it has an alpha channel
    img = img.convert("RGBA")
    
    # Find the bounding box of non-transparent pixels
    bbox = img.getbbox()
    if bbox:
        img = img.crop(bbox)
        
    # Make it a perfect square
    width, height = img.size
    size = max(width, height)
    square_img = Image.new('RGBA', (size, size), (0, 0, 0, 0))
    offset = ((size - width) // 2, (size - height) // 2)
    square_img.paste(img, offset)
    
    # Save as .ico with multiple sizes
    icon_sizes = [(16, 16), (32, 32), (48, 48), (64, 64), (128, 128), (256, 256)]
    square_img.save('public/favicon.ico', format='ICO', sizes=icon_sizes)
    print("Successfully created state of the art favicon.ico from rye8-icon.png")
except Exception as e:
    print(f"Error: {e}")
