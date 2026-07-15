import re

with open('public/ICON-01.svg', 'r') as f:
    svg_data = f.read()

# Make it perfectly square by setting viewBox
# The current viewBox is "0 0 1024 1017"
svg_data = re.sub(r'viewBox="[^"]+"', 'viewBox="0 0 1024 1024"', svg_data)

# Add a gold gradient defs
defs = '''<defs>
    <linearGradient id="gold-grad" x1="0%" y1="0%" x2="100%" y2="100%">
        <stop offset="0%" stop-color="#bf953f" />
        <stop offset="25%" stop-color="#fcf6ba" />
        <stop offset="50%" stop-color="#b38728" />
        <stop offset="75%" stop-color="#fbf5b7" />
        <stop offset="100%" stop-color="#aa771c" />
    </linearGradient>
</defs>'''

svg_data = svg_data.replace('version="1.1">', 'version="1.1">' + defs)
svg_data = svg_data.replace('fill="black"', 'fill="url(#gold-grad)"')

with open('public/favicon.svg', 'w') as f:
    f.write(svg_data)
