svg = '''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024">
    <!-- Deep Black Background -->
    <rect width="1024" height="1024" rx="224" fill="#000000" />
    
    <!-- Premium Platinum Gradient -->
    <defs>
        <linearGradient id="platinum" x1="0%" y1="0%" x2="100%" y2="100%">
            <stop offset="0%" stop-color="#FFFFFF" />
            <stop offset="40%" stop-color="#E4E4E7" />
            <stop offset="100%" stop-color="#71717A" />
        </linearGradient>
    </defs>

    <!-- Sleek, High-End Geometric R -->
    <g fill="url(#platinum)">
        <!-- Vertical Stem -->
        <rect x="300" y="240" width="120" height="544" rx="16" />
        
        <!-- Top Loop with Cutout -->
        <path fill-rule="evenodd" d="M400,240 h180 c140,0 200,60 200,150 c0,90 -60,150 -200,150 h-180 v-300 z M400,340 v100 h160 c70,0 90,-20 90,-50 c0,-30 -20,-50 -90,-50 h-160 z" />
        
        <!-- Elegant Leg (with matching rounded corner look using a path) -->
        <path d="M480,520 l200,264 h150 l-220,-280 z" />
    </g>
</svg>'''
with open('public/favicon.svg', 'w') as f:
    f.write(svg)
