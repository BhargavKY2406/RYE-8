import React, { useState, useEffect } from 'react';
import { Package, Clock, Activity, Play, Calendar, Users, XCircle, MapPin } from 'lucide-react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { useToast } from '../context/ToastContext';
import API from '../api';

const OrdersPage = () => {
    const [activeTab, setActiveTab] = useState('orders');
    const [orders, setOrders] = useState([]);
    const [reservations, setReservations] = useState([]);
    const [loading, setLoading] = useState(true);
    const [simulating, setSimulating] = useState({});
    const { user } = useAuth();
    const navigate = useNavigate();
    const { showToast } = useToast();

    useEffect(() => {
        if (!user) {
            navigate('/');
            return;
        }
        fetchData(true);

        const interval = setInterval(() => {
            if (activeTab === 'orders') {
                fetchData(false);
            }
        }, 3000);

        return () => clearInterval(interval);
    }, [user, navigate, activeTab]);

    const fetchData = async (showLoading = true) => {
        if (showLoading) setLoading(true);
        try {
            const [ordersRes, reservationsRes] = await Promise.all([
                API.getOrders(),
                API.getMyReservations()
            ]);
            if (ordersRes.success) {
                const sorted = ordersRes.data.sort((a, b) => new Date(b.orderDate) - new Date(a.orderDate));
                setOrders(sorted);
            }
            if (reservationsRes.success) {
                setReservations(reservationsRes.data);
            }
        } catch (error) {
            console.error('Failed to fetch data:', error);
        } finally {
            if (showLoading) setLoading(false);
        }
    };

    const handleCancelReservation = async (id) => {
        if (!window.confirm('Are you sure you want to cancel this reservation?')) return;
        try {
            await API.cancelReservation(id);
            showToast('success', 'Reservation cancelled successfully.');
            fetchData(false);
        } catch (error) {
            showToast('error', 'Failed to cancel reservation.');
        }
    };

    const handleSimulate = async (orderId) => {
        setSimulating(prev => ({ ...prev, [orderId]: true }));
        try {
            await API.simulateOrder(orderId);
        } catch (error) {
            console.error('Failed to simulate:', error);
        }
    };

    if (loading) {
        return (
            <div className="loading-spinner" style={{minHeight: '60vh'}}>
                <div className="spinner"></div>
                <p>Loading your activity...</p>
            </div>
        );
    }

    const formatDate = (dateString) => {
        const d = new Date(dateString);
        return d.toLocaleDateString('en-IN', {
            year: 'numeric', month: 'short', day: 'numeric',
            hour: '2-digit', minute: '2-digit'
        });
    };

    const getProgress = (status) => {
        switch(status) {
            case 'PENDING': return 25;
            case 'PREPARING': return 50;
            case 'OUT_FOR_DELIVERY': return 75;
            case 'DELIVERED': return 100;
            case 'CANCELLED': return 0;
            default: return 0;
        }
    };

    return (
        <div className="orders-page main-content">
            <div className="section-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <h2>My Activity <Activity size={24} style={{marginLeft: '12px', color: 'var(--color-primary)'}} className="pulse" /></h2>
                <div style={{ display: 'flex', gap: '10px' }}>
                    <button 
                        className={activeTab === 'orders' ? 'btn-primary' : 'btn-outline'}
                        onClick={() => setActiveTab('orders')}
                    >
                        Orders
                    </button>
                    <button 
                        className={activeTab === 'reservations' ? 'btn-primary' : 'btn-outline'}
                        onClick={() => setActiveTab('reservations')}
                    >
                        Table Reservations
                    </button>
                </div>
            </div>

            {activeTab === 'orders' && (
                orders.length === 0 ? (
                    <div className="empty-state">
                        <Package className="empty-icon" />
                        <p>You haven't placed any orders yet.</p>
                        <button className="btn-primary" style={{marginTop: '16px'}} onClick={() => navigate('/')}>
                            Start Exploring
                        </button>
                    </div>
                ) : (
                    <div className="orders-list">
                        {orders.map(order => {
                            const progress = getProgress(order.status);
                            const isTerminal = order.status === 'DELIVERED' || order.status === 'CANCELLED';

                            return (
                                <div key={order.orderId} className="order-card">
                                    <div className="order-card-header">
                                        <div>
                                            <h3 className="order-restaurant-name">{order.restaurantName}</h3>
                                            <span className="order-id">Order #{order.orderId}</span>
                                        </div>
                                        <div style={{display: 'flex', alignItems: 'center', gap: '16px'}}>
                                            {!isTerminal && (
                                                <button 
                                                    className="btn-outline" 
                                                    style={{padding: '6px 16px', fontSize: '12px'}}
                                                    onClick={() => handleSimulate(order.orderId)}
                                                    disabled={simulating[order.orderId]}
                                                >
                                                    {simulating[order.orderId] ? 'Tracking...' : <><Play size={12} style={{marginRight: '6px'}}/> Track Live</>}
                                                </button>
                                            )}
                                            <span className={`order-status status-${order.status}`}>
                                                {order.status.replace(/_/g, ' ')}
                                            </span>
                                        </div>
                                    </div>
                                    
                                    <div className="tracking-container">
                                        <div className="tracking-bar-bg">
                                            <div className={`tracking-bar-fill fill-${order.status}`} style={{width: `${progress}%`}}></div>
                                        </div>
                                        <div className="tracking-steps">
                                            <span className={progress >= 25 ? 'active' : ''}>Pending</span>
                                            <span className={progress >= 50 ? 'active' : ''}>Preparing</span>
                                            <span className={progress >= 75 ? 'active' : ''}>Out for Delivery</span>
                                            <span className={progress >= 100 ? 'active' : ''}>Delivered</span>
                                        </div>
                                    </div>

                                    <div className="order-card-body">
                                        <div className="order-meta">
                                            <span><Clock size={14} /> {formatDate(order.orderDate)}</span>
                                            <span>Payment: {order.paymentMethod.replace(/_/g, ' ')}</span>
                                        </div>
                                        <ul className="order-items-list">
                                            {order.items.map(item => (
                                                <li key={item.orderItemId}>
                                                    <span>{item.quantity} × {item.itemName}</span>
                                                    <span>₹{item.itemTotal.toFixed(2)}</span>
                                                </li>
                                            ))}
                                        </ul>
                                    </div>
                                    <div className="order-card-footer">
                                        <span className="order-total-label">Total Amount</span>
                                        <span className="order-total-amount">₹{order.totalAmount.toFixed(2)}</span>
                                    </div>
                                </div>
                            );
                        })}
                    </div>
                )
            )}

            {activeTab === 'reservations' && (
                reservations.length === 0 ? (
                    <div className="empty-state">
                        <Calendar className="empty-icon" />
                        <p>You have no upcoming table reservations.</p>
                        <button className="btn-primary" style={{marginTop: '16px'}} onClick={() => navigate('/')}>
                            Book a Table
                        </button>
                    </div>
                ) : (
                    <div className="orders-list">
                        {reservations.map(res => (
                            <div key={res.id} className="order-card">
                                <div className="order-card-header">
                                    <div>
                                        <h3 className="order-restaurant-name">{res.restaurantName}</h3>
                                        <span className="order-id">
                                            <MapPin size={12} style={{marginRight:'4px'}}/> {res.restaurantAddress}
                                        </span>
                                    </div>
                                    <div style={{display: 'flex', alignItems: 'center', gap: '16px'}}>
                                        {res.status === 'CONFIRMED' && (
                                            <button 
                                                className="btn-outline" 
                                                style={{padding: '6px 16px', fontSize: '12px', borderColor: 'var(--color-error)', color: 'var(--color-error)'}}
                                                onClick={() => handleCancelReservation(res.id)}
                                            >
                                                <XCircle size={12} style={{marginRight: '6px'}}/> Cancel
                                            </button>
                                        )}
                                        <span className={`order-status status-${res.status}`}>
                                            {res.status}
                                        </span>
                                    </div>
                                </div>
                                <div className="order-card-body" style={{ display: 'flex', justifyContent: 'space-between', padding: '20px' }}>
                                    <div>
                                        <div style={{ display: 'flex', alignItems: 'center', gap: '8px', marginBottom: '8px', color: 'var(--text-secondary)' }}>
                                            <Calendar size={16} /> <strong>{new Date(res.reservationDate).toLocaleDateString()}</strong>
                                        </div>
                                        <div style={{ display: 'flex', alignItems: 'center', gap: '8px', color: 'var(--text-secondary)' }}>
                                            <Clock size={16} /> <strong>{res.reservationTime}</strong>
                                        </div>
                                    </div>
                                    <div style={{ textAlign: 'right' }}>
                                        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'flex-end', gap: '8px', marginBottom: '8px', color: 'var(--text-secondary)' }}>
                                            <Users size={16} /> <strong>Party of {res.partySize}</strong>
                                        </div>
                                        {res.specialRequests && (
                                            <div style={{ fontSize: '13px', color: 'var(--text-muted)', maxWidth: '200px' }}>
                                                "{res.specialRequests}"
                                            </div>
                                        )}
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                )
            )}
        </div>
    );
};

export default OrdersPage;
