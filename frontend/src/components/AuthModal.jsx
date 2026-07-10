import React, { useState } from 'react';
import { X, User, Lock, Mail, MapPin } from 'lucide-react';
import { useAuth } from '../context/AuthContext';

const AuthModal = ({ isOpen, onClose }) => {
    const { login, register } = useAuth();
    const [isLogin, setIsLogin] = useState(true);
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        email: '',
        address: ''
    });
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(false);

    if (!isOpen) return null;

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setIsLoading(true);

        try {
            if (isLogin) {
                await login(formData.username, formData.password);
            } else {
                await register(formData.username, formData.password, formData.email, formData.address);
            }
            onClose(); // Close on success
        } catch (err) {
            setError(err.message);
        } finally {
            setIsLoading(false);
        }
    };

    const toggleMode = () => {
        setIsLogin(!isLogin);
        setError(null);
    };

    return (
        <div className="modal-overlay active" onClick={onClose}>
            <div className="modal" onClick={e => e.stopPropagation()}>
                <button className="modal-close" onClick={onClose}><X size={24} /></button>
                <div className="modal-header">
                    <h2>{isLogin ? 'Welcome Back' : 'Create Account'}</h2>
                    <p>{isLogin ? 'Sign in to order your favorite food' : 'Join Rye-8 for the best local eats'}</p>
                </div>
                
                {error && <div className="modal-error">{error}</div>}

                <form className="modal-body" onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>Username</label>
                        <div className="input-with-icon">
                            <User size={18} className="input-icon" />
                            <input 
                                type="text" 
                                name="username" 
                                placeholder="Enter username" 
                                required
                                value={formData.username}
                                onChange={handleChange}
                            />
                        </div>
                    </div>

                    {!isLogin && (
                        <div className="form-group">
                            <label>Email</label>
                            <div className="input-with-icon">
                                <Mail size={18} className="input-icon" />
                                <input 
                                    type="email" 
                                    name="email" 
                                    placeholder="Enter email address" 
                                    required
                                    value={formData.email}
                                    onChange={handleChange}
                                />
                            </div>
                        </div>
                    )}

                    <div className="form-group">
                        <label>Password</label>
                        <div className="input-with-icon">
                            <Lock size={18} className="input-icon" />
                            <input 
                                type="password" 
                                name="password" 
                                placeholder="Enter password" 
                                required
                                value={formData.password}
                                onChange={handleChange}
                            />
                        </div>
                    </div>

                    {!isLogin && (
                        <div className="form-group">
                            <label>Delivery Address (Optional)</label>
                            <div className="input-with-icon">
                                <MapPin size={18} className="input-icon" />
                                <input 
                                    type="text" 
                                    name="address" 
                                    placeholder="Enter your address"
                                    value={formData.address}
                                    onChange={handleChange}
                                />
                            </div>
                        </div>
                    )}

                    <button type="submit" className="btn-primary" style={{ width: '100%' }} disabled={isLoading}>
                        {isLoading ? 'Processing...' : (isLogin ? 'Sign In' : 'Sign Up')}
                    </button>
                </form>

                <div className="modal-footer">
                    <p>
                        {isLogin ? "Don't have an account? " : "Already have an account? "}
                        <button type="button" className="text-link" onClick={toggleMode}>
                            {isLogin ? 'Sign Up' : 'Sign In'}
                        </button>
                    </p>
                </div>
            </div>
        </div>
    );
};

export default AuthModal;
