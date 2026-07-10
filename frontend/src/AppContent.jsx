import React, { useState, Suspense, lazy } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import CartSidebar from './components/CartSidebar';
import AuthModal from './components/AuthModal';
import PageLoader from './components/PageLoader';

// Lazy load page components
const HomePage = lazy(() => import('./pages/HomePage'));
const RestaurantPage = lazy(() => import('./pages/RestaurantPage'));
const OrdersPage = lazy(() => import('./pages/OrdersPage'));
const AdminDashboard = lazy(() => import('./pages/AdminDashboard'));

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
                <Suspense fallback={<PageLoader />}>
                    <Routes>
                        <Route path="/" element={<HomePage searchQuery={searchQuery} setSearchQuery={setSearchQuery} />} />
                        <Route path="/restaurant/:id" element={<RestaurantPage onOpenAuth={() => setIsAuthOpen(true)} />} />
                        <Route path="/orders" element={<OrdersPage />} />
                        <Route path="/admin" element={<AdminDashboard />} />
                    </Routes>
                </Suspense>
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
