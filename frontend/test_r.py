svg = '''<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024">
    <defs>
        <linearGradient id="r-grad" x1="0%" y1="100%" x2="100%" y2="0%">
            <stop offset="0%" stop-color="#FF3366" />
            <stop offset="50%" stop-color="#FF9933" />
            <stop offset="100%" stop-color="#FFD700" />
        </linearGradient>
    </defs>
    <rect width="1024" height="1024" rx="220" fill="#0A0A0B" />
    <path d="M360,760 L360,280 L560,280 C700,280 780,340 780,440 C780,540 700,600 560,600 L360,600" 
          stroke="url(#r-grad)" stroke-width="140" stroke-linecap="round" stroke-linejoin="round" fill="none" />
    <path d="M520,600 L760,760" 
          stroke="url(#r-grad)" stroke-width="140" stroke-linecap="round" stroke-linejoin="round" fill="none" />
</svg>'''
with open('public/favicon.svg', 'w') as f:
    f.write(svg)
