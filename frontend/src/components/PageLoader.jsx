import React from 'react';

const PageLoader = () => {
    return (
        <div style={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            justifyContent: 'center',
            height: '100vh',
            width: '100vw',
            background: 'var(--bg-main)',
            position: 'fixed',
            top: 0,
            left: 0,
            zIndex: 9999,
        }}>
            <div style={{
                fontSize: '48px', 
                fontWeight: '400', 
                letterSpacing: '12px', 
                fontFamily: '"Didot", "Bodoni MT", "Optima", "Times New Roman", serif',
                background: 'linear-gradient(120deg, #c5a059 0%, #ffd770 35%, #c5a059 70%, #8b6914 100%)',
                WebkitBackgroundClip: 'text',
                WebkitTextFillColor: 'transparent',
                backgroundClip: 'text',
                color: 'transparent',
                display: 'inline-block',
                lineHeight: '1.1',
                paddingRight: '12px',
                animation: 'shimmerPulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite'
            }}>
                RYE-8
            </div>
            <span style={{
                marginTop: '12px',
                fontSize: '12px', 
                color: 'var(--text-secondary)', 
                letterSpacing: '4px', 
                textTransform: 'uppercase', 
                fontWeight: '500',
                animation: 'pulseOpacity 2s ease-in-out infinite alternate'
            }}>Curating Excellence...</span>
            
            <style>{`
                @keyframes pulseOpacity {
                    from { opacity: 0.3; }
                    to { opacity: 0.8; }
                }
            `}</style>
        </div>
    );
};

export default PageLoader;
