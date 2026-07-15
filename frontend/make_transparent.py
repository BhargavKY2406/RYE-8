from PIL import Image
import sys

def remove_black_background(input_path, output_path):
    img = Image.open(input_path).convert("RGBA")
    datas = img.getdata()
    
    newData = []
    # Make black/near-black pixels transparent
    for item in datas:
        # Check if the pixel is dark (r, g, b values are low)
        # Using a threshold of 30 out of 255
        if item[0] < 30 and item[1] < 30 and item[2] < 30:
            # Fully transparent
            newData.append((0, 0, 0, 0))
        elif item[0] < 80 and item[1] < 80 and item[2] < 80:
            # Semi-transparent for smooth blending of edges
            alpha = int(((item[0] + item[1] + item[2]) / 240.0) * 255)
            newData.append((item[0], item[1], item[2], alpha))
        else:
            newData.append(item)
            
    img.putdata(newData)
    
    # Crop the image to its bounding box to remove excess transparent space
    bbox = img.getbbox()
    if bbox:
        img = img.crop(bbox)
        
    img.save(output_path, "PNG")
    print(f"Saved transparent logo to {output_path}")

remove_black_background(
    r'C:\Users\Prince\.gemini\antigravity\brain\9a49f3e2-bc5d-4d86-89e3-f50407ccfccc\state_of_art_r8_1783705088297.jpg',
    r'public\r8-logo.png'
)
