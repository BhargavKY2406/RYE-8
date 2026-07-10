import React, { useState, useEffect } from 'react';
import { Package, Users, Activity, Settings, Search, Edit2 } from 'lucide-react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { useToast } from '../context/ToastContext';
import API from '../api';

const AdminDashboard = () => {
    const [activeTab, setActiveTab] = useState('orders');
    const [orders, setOrders] = useState([]);
    const [users, setUsers] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchTerm, setSearchTerm] = useState('');
    const { user } = useAuth();
    const navigate = useNavigate();
    const { showToast } = useToast();

    useEffect(() => {
        if (!user || user.role !== 'ADMIN') {
            showToast('error', 'Access denied. Admin only.');
            navigate('/');
            return;
        }
        fetchData();
    }, [user, navigate]);

    const fetchData = async () => {
        setLoading(true);
        try {
            if (activeTab === 'orders') {
                const res = await API.getAdminOrders();
                if (res.success) setOrders(res.data);
            } else if (activeTab === 'users') {
                const res = await API.getAdminUsers();
                if (res.success) setUsers(res.data);
            }
        } catch (error) {
            console.error('Failed to fetch admin data:', error);
            showToast('error', 'Failed to fetch admin data.');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (user && user.role === 'ADMIN') {
            fetchData();
        }
    }, [activeTab]);

    const handleStatusChange = async (orderId, newStatus) => {
        try {
            await API.updateAdminOrderStatus(orderId, newStatus);
            showToast('success', `Order #${orderId} status updated to ${newStatus}`);
            // Update local state without refetching to feel faster
            setOrders(prevOrders => 
                prevOrders.map(o => o.orderId === orderId ? { ...o, status: newStatus } : o)
            );
        } catch (error) {
            showToast('error', 'Failed to update order status');
        }
    };

    const filteredOrders = orders.filter(o => 
        o.orderId.toString().includes(searchTerm) || 
        o.restaurantName.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const filteredUsers = users.filter(u => 
        u.username.toLowerCase().includes(searchTerm.toLowerCase()) || 
        u.email.toLowerCase().includes(searchTerm.toLowerCase())
    );

    const formatDate = (dateString) => {
        return new Date(dateString).toLocaleDateString('en-IN', {
            year: 'numeric', month: 'short', day: 'numeric',
            hour: '2-digit', minute: '2-digit'
        });
    };

    if (loading && orders.length === 0 && users.length === 0) {
        return (
            <div className="loading-spinner" style={{minHeight: '60vh'}}>
                <div className="spinner"></div>
                <p>Loading Admin Dashboard...</p>
            </div>
        );
    }

    return (
        <div className="admin-dashboard main-content">
            <div className="section-header" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '16px' }}>
                <h2 style={{ display: 'flex', alignItems: 'center', gap: '12px' }}>
                    <Settings size={28} className="pulse" style={{ color: 'var(--color-primary)' }}/> 
                    Admin Dashboard
                </h2>
                
                <div className="search-bar" style={{ width: '300px' }}>
                    <Search className="search-icon" size={18} />
                    <input 
                        type="text" 
                        placeholder={`Search ${activeTab}...`} 
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                    />
                </div>
            </div>

            <div className="admin-tabs" style={{ display: 'flex', gap: '10px', marginBottom: '24px' }}>
                <button 
                    className={activeTab === 'orders' ? 'btn-primary' : 'btn-outline'}
                    onClick={() => setActiveTab('orders')}
                    style={{ flex: 1, padding: '12px', display: 'flex', justifyContent: 'center', alignItems: 'center', gap: '8px' }}
                >
                    <Package size={18}/> Orders Management
                </button>
                <button 
                    className={activeTab === 'users' ? 'btn-primary' : 'btn-outline'}
                    onClick={() => setActiveTab('users')}
                    style={{ flex: 1, padding: '12px', display: 'flex', justifyContent: 'center', alignItems: 'center', gap: '8px' }}
                >
                    <Users size={18}/> Users Management
                </button>
            </div>

            {activeTab === 'orders' && (
                <div className="admin-table-container">
                    <table className="admin-table" style={{ width: '100%', borderCollapse: 'collapse', backgroundColor: 'var(--bg-secondary)', borderRadius: '12px', overflow: 'hidden', boxShadow: 'var(--shadow-md)' }}>
                        <thead style={{ backgroundColor: 'var(--border-color)', color: 'var(--text-primary)', textAlign: 'left' }}>
                            <tr>
                                <th style={{ padding: '16px' }}>Order ID</th>
                                <th style={{ padding: '16px' }}>Date</th>
                                <th style={{ padding: '16px' }}>Restaurant</th>
                                <th style={{ padding: '16px' }}>User ID</th>
                                <th style={{ padding: '16px' }}>Total (₹)</th>
                                <th style={{ padding: '16px' }}>Status</th>
                                <th style={{ padding: '16px' }}>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filteredOrders.length === 0 ? (
                                <tr><td colSpan="7" style={{ textAlign: 'center', padding: '32px' }}>No orders found.</td></tr>
                            ) : (
                                filteredOrders.map(order => (
                                    <tr key={order.orderId} style={{ borderBottom: '1px solid var(--border-color)' }}>
                                        <td style={{ padding: '16px', fontWeight: 'bold' }}>#{order.orderId}</td>
                                        <td style={{ padding: '16px', color: 'var(--text-secondary)', fontSize: '14px' }}>{formatDate(order.orderDate)}</td>
                                        <td style={{ padding: '16px' }}>{order.restaurantName}</td>
                                        <td style={{ padding: '16px' }}>{order.userId}</td>
                                        <td style={{ padding: '16px', fontWeight: '600' }}>₹{order.totalAmount.toFixed(2)}</td>
                                        <td style={{ padding: '16px' }}>
                                            <span className={`order-status status-${order.status}`} style={{ fontSize: '11px', padding: '4px 8px' }}>
                                                {order.status.replace(/_/g, ' ')}
                                            </span>
                                        </td>
                                        <td style={{ padding: '16px' }}>
                                            <select 
                                                value={order.status}
                                                onChange={(e) => handleStatusChange(order.orderId, e.target.value)}
                                                style={{ padding: '6px 12px', borderRadius: '6px', backgroundColor: 'var(--bg-primary)', border: '1px solid var(--border-color)', color: 'var(--text-primary)' }}
                                            >
                                                <option value="PENDING">Pending</option>
                                                <option value="PREPARING">Preparing</option>
                                                <option value="OUT_FOR_DELIVERY">Out for Delivery</option>
                                                <option value="DELIVERED">Delivered</option>
                                                <option value="CANCELLED">Cancelled</option>
                                            </select>
                                        </td>
                                    </tr>
                                ))
                            )}
                        </tbody>
                    </table>
                </div>
            )}

            {activeTab === 'users' && (
                <div className="admin-table-container">
                    <table className="admin-table" style={{ width: '100%', borderCollapse: 'collapse', backgroundColor: 'var(--bg-secondary)', borderRadius: '12px', overflow: 'hidden', boxShadow: 'var(--shadow-md)' }}>
                        <thead style={{ backgroundColor: 'var(--border-color)', color: 'var(--text-primary)', textAlign: 'left' }}>
                            <tr>
                                <th style={{ padding: '16px' }}>User ID</th>
                                <th style={{ padding: '16px' }}>Username</th>
                                <th style={{ padding: '16px' }}>Email</th>
                                <th style={{ padding: '16px' }}>Role</th>
                                <th style={{ padding: '16px' }}>Joined Date</th>
                                <th style={{ padding: '16px' }}>Last Login</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filteredUsers.length === 0 ? (
                                <tr><td colSpan="6" style={{ textAlign: 'center', padding: '32px' }}>No users found.</td></tr>
                            ) : (
                                filteredUsers.map(u => (
                                    <tr key={u.userId} style={{ borderBottom: '1px solid var(--border-color)' }}>
                                        <td style={{ padding: '16px', fontWeight: 'bold' }}>#{u.userId}</td>
                                        <td style={{ padding: '16px' }}>{u.username}</td>
                                        <td style={{ padding: '16px' }}>{u.email}</td>
                                        <td style={{ padding: '16px' }}>
                                            <span style={{ 
                                                backgroundColor: u.role === 'ADMIN' ? 'var(--color-primary)' : 'var(--bg-primary)',
                                                color: u.role === 'ADMIN' ? '#fff' : 'var(--text-primary)',
                                                padding: '4px 10px', borderRadius: '20px', fontSize: '12px', fontWeight: '600' 
                                            }}>
                                                {u.role}
                                            </span>
                                        </td>
                                        <td style={{ padding: '16px', color: 'var(--text-secondary)', fontSize: '14px' }}>{formatDate(u.createdDate)}</td>
                                        <td style={{ padding: '16px', color: 'var(--text-secondary)', fontSize: '14px' }}>{u.lastLoginDate ? formatDate(u.lastLoginDate) : 'Never'}</td>
                                    </tr>
                                ))
                            )}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    );
};

export default AdminDashboard;
