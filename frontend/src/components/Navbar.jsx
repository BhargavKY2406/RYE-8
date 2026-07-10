import React from 'react';
import { ShoppingCart, User, Package, LogOut, Sun, Moon, Shield } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import { useAuth } from '../context/AuthContext';
import { useTheme } from '../context/ThemeContext';

const Navbar = ({ onOpenCart, onOpenAuth }) => {
    const { getTotalItems } = useCart();
    const { user, logout } = useAuth();
    const { theme, toggleTheme } = useTheme();
    const navigate = useNavigate();

    const handleOrdersClick = () => {
        navigate('/orders');
    };

    const handleNavClick = (e, hash) => {
        e.preventDefault();
        if (window.location.pathname !== '/') {
            navigate('/');
            setTimeout(() => {
                const element = document.getElementById(hash.replace('#', ''));
                if (element) {
                    element.scrollIntoView({ behavior: 'smooth' });
                }
            }, 300);
        } else {
            const element = document.getElementById(hash.replace('#', ''));
            if (element) {
                element.scrollIntoView({ behavior: 'smooth' });
            }
        }
    };

    return (
        <nav className="navbar">
            <div className="nav-container-top" style={{width: '100%', maxWidth: '1200px', margin: '0 auto', display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '0 24px'}}>
                <Link 
                    to="/" 
                    className="nav-logo" 
                    onClick={() => {
                        if (window.location.pathname === '/') {
                            window.scrollTo({ top: 0, behavior: 'smooth' });
                        }
                    }}
                    style={{display: 'flex', alignItems: 'center', gap: '15px', textDecoration: 'none'}}
                >
                    <div style={{display: 'flex', flexDirection: 'column', gap: '4px'}}>
                        <style>
                            {`
                                @keyframes shimmerLogo {
                                    0% { background-position: -200% center; }
                                    100% { background-position: 200% center; }
                                }
                                .luxury-animated-logo {
                                    font-size: 28px;
                                    font-weight: 400;
                                    letter-spacing: 8px;
                                    font-family: "Didot", "Bodoni MT", "Optima", "Times New Roman", serif;
                                    background: linear-gradient(
                                        110deg,
                                        #8A6B0B 0%,
                                        #D4AF37 25%,
                                        #FFF2CD 50%,
                                        #D4AF37 75%,
                                        #8A6B0B 100%
                                    );
                                    background-size: 200% auto;
                                    -webkit-background-clip: text;
                                    -webkit-text-fill-color: transparent;
                                    background-clip: text;
                                    color: transparent;
                                    display: inline-block;
                                    line-height: 1.1;
                                    padding-right: 8px;
                                    animation: shimmerLogo 6s linear infinite;
                                }
                            `}
                        </style>
                        <div className="luxury-animated-logo">
                            RYE-8
                        </div>
                        <span style={{
                            fontSize: '9px', 
                            color: 'var(--text-secondary)', 
                            letterSpacing: '3px', 
                            textTransform: 'uppercase', 
                            fontWeight: '500',
                            paddingLeft: '2px'
                        }}>Exceptional Meals, Every Time.</span>
                    </div>
                </Link>

                <div className="nav-actions">
                    <button className="theme-btn" onClick={toggleTheme} title="Toggle Theme">
                        {theme === 'light' ? <Moon size={20} /> : <Sun size={20} />}
                    </button>
                    <button className="cart-btn" onClick={onOpenCart} title="Cart">
                        <ShoppingCart size={20} />
                        <span className={`cart-badge ${getTotalItems() > 0 ? 'bump' : ''}`}>
                            {getTotalItems()}
                        </span>
                    </button>

                    {user ? (
                        <>
                            {user.role === 'ADMIN' && (
                                <button className="cart-btn admin-btn" onClick={() => navigate('/admin')} title="Admin Dashboard">
                                    <Shield size={20} />
                                    <span className="hide-mobile" style={{color: 'var(--color-primary)'}}>Admin</span>
                                </button>
                            )}
                            <button className="cart-btn" onClick={handleOrdersClick} title="My Activity">
                                <Package size={20} />
                                <span className="hide-mobile">My Activity</span>
                            </button>
                            <button className="cart-btn" onClick={logout} title="Sign Out">
                                <LogOut size={20} />
                                <span className="hide-mobile">{user.username}</span>
                            </button>
                        </>
                    ) : (
                        <button className="btn-primary" style={{padding: '10px 20px'}} onClick={onOpenAuth}>
                            <User size={18} />
                            Sign In
                        </button>
                    )}
                </div>
            </div>

            <div className="nav-container-bottom" style={{width: '100%', maxWidth: '1200px', margin: '15px auto 0', display: 'flex', justifyContent: 'center', borderTop: '1px solid var(--border-color)', paddingTop: '12px'}}>
                <div className="nav-links" style={{display: 'flex', gap: '40px', justifyContent: 'center'}}>
                    <a href="#top-rated" className="nav-link" onClick={(e) => handleNavClick(e, '#top-rated')}>Top Rated</a>
                    <a href="#chefs-favorites" className="nav-link" onClick={(e) => handleNavClick(e, '#chefs-favorites')}>Chef's Favorites</a>
                    <a href="#cuisines" className="nav-link" onClick={(e) => handleNavClick(e, '#cuisines')}>Cuisines</a>
                    <a href="#faq" className="nav-link" onClick={(e) => handleNavClick(e, '#faq')}>FAQ</a>
                    <a href="#contact-us" className="nav-link" onClick={(e) => handleNavClick(e, '#contact-us')}>Contact Us</a>
                </div>
            </div>
        </nav>
    );
};

export default Navbar;
