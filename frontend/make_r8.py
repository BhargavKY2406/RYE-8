svg = '''<svg xmlns="http://www.w3.org/2000/svg" viewBox="160 80 900 840">
    <defs>
        <linearGradient id="gold" x1="0%" y1="0%" x2="100%" y2="100%">
            <stop offset="0%" stop-color="#D4AF37" />
            <stop offset="50%" stop-color="#FDF19B" />
            <stop offset="100%" stop-color="#8A6B0B" />
        </linearGradient>
    </defs>
    
    <g stroke="url(#gold)" stroke-width="130" stroke-linecap="round" stroke-linejoin="round" fill="none">
        <!-- R -->
        <path d="M260,820 L260,180" />
        <path d="M260,180 L400,180 C560,180 620,280 620,400 C620,520 560,600 400,600 L260,600" />
        <path d="M380,600 L620,820" />
        
        <!-- 8 -->
        <circle cx="820" cy="360" r="120" />
        <circle cx="820" cy="640" r="140" />
    </g>
</svg>'''
with open('public/r8-logo.svg', 'w') as f:
    f.write(svg)
