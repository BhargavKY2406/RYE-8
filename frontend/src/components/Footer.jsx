import React from 'react';
import { Hash, MessageCircle, Camera, Mail, Phone, MapPin } from 'lucide-react';
import { Link } from 'react-router-dom';

const Footer = () => {
    return (
        <footer className="footer">
            <div className="footer-gradient-bar"></div>
            <div className="footer-container">
                <div className="footer-grid">
                    <div className="footer-col brand-col">
                    <Link to="/" className="footer-logo nav-logo" style={{display: 'flex', alignItems: 'center', gap: '15px', marginBottom: '20px', textDecoration: 'none'}}>
                        <div style={{display: 'flex', flexDirection: 'column', gap: '4px'}}>
                            <div style={{
                                fontSize: '28px', 
                                fontWeight: '400', 
                                letterSpacing: '8px', 
                                fontFamily: '"Didot", "Bodoni MT", "Optima", "Times New Roman", serif',
                                background: 'linear-gradient(120deg, #c5a059 0%, #ffd770 35%, #c5a059 70%, #8b6914 100%)',
                                WebkitBackgroundClip: 'text',
                                WebkitTextFillColor: 'transparent',
                                backgroundClip: 'text',
                                color: 'transparent',
                                display: 'inline-block',
                                lineHeight: '1.1',
                                paddingRight: '8px'
                            }}>
                                ZYRA
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
                    <p className="footer-tagline">
                        Experience the best restaurants in your city. From local favorites to global cuisines, get it hot and fresh to your door.
                    </p>
                        <div className="social-links">
                            <a href="#!" onClick={(e) => e.preventDefault()} className="social-link"><Hash size={20} /></a>
                            <a href="#!" onClick={(e) => e.preventDefault()} className="social-link"><MessageCircle size={20} /></a>
                            <a href="#!" onClick={(e) => e.preventDefault()} className="social-link"><Camera size={20} /></a>
                        </div>
                    </div>

                    <div className="footer-col">
                        <h4 className="footer-title">Quick Links</h4>
                        <ul className="footer-links">
                            <li><Link to="/">Home</Link></li>
                            <li><Link to="/">Restaurants</Link></li>
                            <li><Link to="/orders">My Orders</Link></li>
                            <li><a href="#!" onClick={(e) => e.preventDefault()}>About Us</a></li>
                        </ul>
                    </div>

                    <div className="footer-col">
                        <h4 className="footer-title">Cuisines</h4>
                        <ul className="footer-links">
                            <li><a href="#!" onClick={(e) => e.preventDefault()}>Indian</a></li>
                            <li><a href="#!" onClick={(e) => e.preventDefault()}>Italian</a></li>
                            <li><a href="#!" onClick={(e) => e.preventDefault()}>Chinese</a></li>
                            <li><a href="#!" onClick={(e) => e.preventDefault()}>American</a></li>
                            <li><a href="#!" onClick={(e) => e.preventDefault()}>Healthy</a></li>
                        </ul>
                    </div>

                    <div className="footer-col contact-col">
                        <h4 className="footer-title">Contact Us</h4>
                        <ul className="footer-contact">
                            <li>
                                <MapPin size={18} className="contact-icon" />
                                <span>123 Tech Park, Bangalore, India</span>
                            </li>
                            <li>
                                <Phone size={18} className="contact-icon" />
                                <span>+91 98765 43210</span>
                            </li>
                            <li>
                                <Mail className="contact-icon" size={18} />
                                <span>support@zyra.com</span>
                            </li>
                        </ul>
                    </div>
                </div>

                <div className="footer-bottom">
                    <p>&copy; {new Date().getFullYear()} Zyra. All rights reserved.</p>
                    <div className="footer-legal">
                        <a href="#!" onClick={(e) => e.preventDefault()}>Privacy Policy</a>
                        <a href="#!" onClick={(e) => e.preventDefault()}>Terms of Service</a>
                    </div>
                </div>
            </div>
        </footer>
    );
};

export default Footer;
