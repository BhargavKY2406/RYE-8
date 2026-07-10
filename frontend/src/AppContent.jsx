import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import CartSidebar from './components/CartSidebar';
import AuthModal from './components/AuthModal';
import HomePage from './pages/HomePage';
import RestaurantPage from './pages/RestaurantPage';
import OrdersPage from './pages/OrdersPage';

const AppContent = () => {
    const [isCartOpen, setIsCartOpen] = useState(false);
    const [isAuthOpen, setIsAuthOpen] = useState(false);
    const [searchQuery, setSearchQuery] = useState('');

    return (
        <div className="app-container">
            <Navbar 
                onOpenCart={() => setIsCartOpen(true)} 
                onOpenAuth={() => setIsAuthOpen(true)}
            />
            
            <main className="content-wrap">
                <Routes>
                    <Route path="/" element={<HomePage searchQuery={searchQuery} setSearchQuery={setSearchQuery} />} />
                    <Route path="/restaurant/:id" element={<RestaurantPage onOpenAuth={() => setIsAuthOpen(true)} />} />
                    <Route path="/orders" element={<OrdersPage />} />
                </Routes>
            </main>
            
            <Footer />
            
            <CartSidebar 
                isOpen={isCartOpen} 
                onClose={() => setIsCartOpen(false)}
                onOpenAuth={() => setIsAuthOpen(true)}
            />
            
            <AuthModal 
                isOpen={isAuthOpen}
                onClose={() => setIsAuthOpen(false)}
            />
        </div>
    );
};

export default AppContent;
