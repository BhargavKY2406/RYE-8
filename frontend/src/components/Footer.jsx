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
                    <Link to="/" className="footer-logo nav-logo" style={{display: 'flex', alignItems: 'center', gap: '12px', marginBottom: '20px', textDecoration: 'none'}}>
                        <img src="/rye8-icon.png" alt="R8 Icon" style={{height: '50px', width: 'auto', objectFit: 'contain'}} />
                        <div style={{display: 'flex', flexDirection: 'column'}}>
                            <span className="luxury-logo-text" style={{fontSize: '24px', lineHeight: '1', marginBottom: '4px'}}>RYE-8</span>
                            <span style={{fontSize: '11px', color: 'var(--text-secondary)', letterSpacing: '1px', textTransform: 'uppercase', fontWeight: '600'}}>Exceptional Meals, Every Time.</span>
                        </div>
                    </Link>
                    <p className="footer-tagline">
                        Experience the best restaurants in your city. From local favorites to global cuisines, get it hot and fresh to your door.
                    </p>
                        <div className="social-links">
                            <a href="#" className="social-link"><Hash size={20} /></a>
                            <a href="#" className="social-link"><MessageCircle size={20} /></a>
                            <a href="#" className="social-link"><Camera size={20} /></a>
                        </div>
                    </div>

                    <div className="footer-col">
                        <h4 className="footer-title">Quick Links</h4>
                        <ul className="footer-links">
                            <li><Link to="/">Home</Link></li>
                            <li><Link to="/">Restaurants</Link></li>
                            <li><Link to="/orders">My Orders</Link></li>
                            <li><a href="#">About Us</a></li>
                        </ul>
                    </div>

                    <div className="footer-col">
                        <h4 className="footer-title">Cuisines</h4>
                        <ul className="footer-links">
                            <li><a href="#">Indian</a></li>
                            <li><a href="#">Italian</a></li>
                            <li><a href="#">Chinese</a></li>
                            <li><a href="#">American</a></li>
                            <li><a href="#">Healthy</a></li>
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
                                <span>support@rye-8.com</span>
                            </li>
                        </ul>
                    </div>
                </div>

                <div className="footer-bottom">
                    <p>&copy; {new Date().getFullYear()} Rye-8. All rights reserved.</p>
                    <div className="footer-legal">
                        <a href="#">Privacy Policy</a>
                        <a href="#">Terms of Service</a>
                    </div>
                </div>
            </div>
        </footer>
    );
};

export default Footer;
