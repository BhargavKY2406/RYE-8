import React from 'react';
import { ShoppingCart, User, Package, LogOut, Sun, Moon } from 'lucide-react';
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
                    <img src="/rye8-icon.png" alt="R8 Icon" style={{height: '42px', width: 'auto', objectFit: 'contain'}} />
                    <div style={{display: 'flex', flexDirection: 'column'}}>
                        <span className="luxury-logo-text" style={{fontSize: '20px', lineHeight: '1', marginBottom: '4px'}}>RYE-8</span>
                        <span style={{fontSize: '10px', color: 'var(--text-secondary)', letterSpacing: '1px', textTransform: 'uppercase', fontWeight: '600'}}>Exceptional Meals, Every Time.</span>
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
