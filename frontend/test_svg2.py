import re

with open('public/ICON-01.svg', 'r') as f:
    svg_data = f.read()

# Make it perfectly square by setting viewBox, and give it some padding so glows don't get clipped
svg_data = re.sub(r'viewBox="[^"]+"', 'viewBox="-100 -100 1224 1224"', svg_data)

defs_and_styles = '''
<defs>
    <!-- Ultra Premium Gold Gradient -->
    <linearGradient id="gold-grad" x1="0%" y1="0%" x2="100%" y2="100%">
        <stop offset="0%" stop-color="#D4AF37" />
        <stop offset="40%" stop-color="#FDF19B" />
        <stop offset="60%" stop-color="#C5A017" />
        <stop offset="100%" stop-color="#8A6B0B" />
    </linearGradient>

    <!-- 3D Bevel/Emboss Filter for Premium Feel -->
    <filter id="bevel" x="-20%" y="-20%" width="140%" height="140%">
        <feGaussianBlur in="SourceAlpha" stdDeviation="8" result="blur" />
        <feSpecularLighting in="blur" surfaceScale="5" specularConstant="1" specularExponent="20" lighting-color="#FFFFFF" result="specOut">
            <fePointLight x="-5000" y="-10000" z="20000" />
        </feSpecularLighting>
        <feComposite in="specOut" in2="SourceAlpha" operator="in" result="specOut" />
        <feComposite in="SourceGraphic" in2="specOut" operator="arithmetic" k1="0" k2="1" k3="1" k4="0" result="litPaint" />
        <!-- Add a subtle drop shadow -->
        <feDropShadow dx="0" dy="15" stdDeviation="15" flood-color="#000" flood-opacity="0.5" />
    </filter>

    <!-- Animated Shine Overlay -->
    <linearGradient id="shine" x1="0%" y1="0%" x2="200%" y2="0%">
        <stop offset="0%" stop-color="rgba(255,255,255,0)" />
        <stop offset="45%" stop-color="rgba(255,255,255,0)" />
        <stop offset="50%" stop-color="rgba(255,255,255,0.8)" />
        <stop offset="55%" stop-color="rgba(255,255,255,0)" />
        <stop offset="100%" stop-color="rgba(255,255,255,0)" />
    </linearGradient>
</defs>

<style>
    .base-path {
        fill: url(#gold-grad);
        filter: url(#bevel);
        transform-origin: center;
        animation: pulse 4s cubic-bezier(0.4, 0, 0.2, 1) infinite alternate;
    }
    .shine-path {
        fill: url(#shine);
        pointer-events: none;
    }
    
    @keyframes pulse {
        0% { transform: scale(0.95); }
        100% { transform: scale(1.05); }
    }
    
    /* CSS animation for the shine effect sweeping across */
    @keyframes sweep {
        0% { transform: translateX(-100%) skewX(-20deg); }
        100% { transform: translateX(100%) skewX(-20deg); }
    }
</style>
'''

# Extract the path d="" string
path_match = re.search(r'<path d="([^"]+)"', svg_data)
d_string = path_match.group(1) if path_match else ""

# Rebuild SVG
new_svg = f'''<svg xmlns="http://www.w3.org/2000/svg" width="100%" height="100%" viewBox="-100 -100 1224 1224" version="1.1">
    {defs_and_styles}
    <!-- Base Logo with Gold Gradient, 3D Bevel, Drop Shadow, and Pulse animation -->
    <path class="base-path" d="{d_string}" />
    
    <!-- Exact duplicate of the path, but filled with the sweeping animated shine -->
    <path class="shine-path" d="{d_string}">
        <animate attributeName="opacity" values="0; 1; 1; 0" keyTimes="0; 0.2; 0.8; 1" dur="3s" repeatCount="indefinite" />
        <animateTransform attributeName="transform" type="translate" from="-1200 0" to="1200 0" dur="3s" repeatCount="indefinite" />
    </path>
</svg>'''

with open('public/favicon.svg', 'w') as f:
    f.write(new_svg)
