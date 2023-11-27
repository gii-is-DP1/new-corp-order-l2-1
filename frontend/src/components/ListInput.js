import React, { useState } from 'react';

const ListInput = ({ onAddItem }) => {
    const [newItem, setNewItem] = useState('');

    const handleAddItem = () => {
        if (newItem.trim() !== '') {
            onAddItem(newItem);
            setNewItem('');
        }
    };

    return (
        <div style={{ display: 'flex', marginBottom: '10px' }}>
            <input
                type="text"
                value={newItem}
                onChange={(e) => setNewItem(e.target.value)}
                placeholder="Escribe algo..."
                style={{
                    flex: 1,
                    padding: '8px',
                    border: '1px solid #ccc',
                    borderRadius: '4px',
                }}
            />
            <button
                onClick={handleAddItem}
                style={{
                    marginLeft: '8px',
                    padding: '8px',
                    backgroundColor: '#4caf50',
                    color: 'white',
                    border: 'none',
                    borderRadius: '4px',
                    cursor: 'pointer',
                }}
            >
                Agregar
            </button>
        </div>
    );
};

const ListItem = ({ items }) => {
    return (
        <div>
            <h2>Lista de Elementos</h2>
            <ul>
                {items.map((item, index) => (
                    <div
                        key={index}
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            backgroundColor: 'white',
                            padding: '10px',
                            marginBottom: '5px',
                            borderRadius: '4px',
                            boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
                        }}
                    >
                        {item.icon && <span style={{ marginRight: '10px' }}>{item.icon}</span>}
                        <span style={{ flex: 1 }}>{item.text}</span>
                        {item.button && (
                            <button
                                onClick={item.button.onClick}
                                style={{
                                    padding: '8px',
                                    backgroundColor: '#4caf50',
                                    color: 'white',
                                    border: 'none',
                                    borderRadius: '4px',
                                    cursor: 'pointer',
                                }}
                            >
                                {item.button.label}
                            </button>
                        )}
                    </div>
                ))}
            </ul>
        </div>
    );
};

const AchievementList = ({ achievement, onShare, onDelete }) => {
    const { icon, text } = achievement;

    return (
        <div
            style={{
                display: 'flex',
                alignItems: 'center',
                backgroundColor: 'white',
                padding: '10px',
                marginBottom: '10px',
                borderRadius: '4px',
                boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
            }}
        >
            <div style={{ marginRight: '10px' }}>
                {icon}
            </div>

            <div style={{ flex: 1 }}>
                <p>{text}</p>
            </div>

            <div>
                <button
                    onClick={onShare}
                    style={{
                        marginRight: '5px',
                        padding: '8px',
                        backgroundColor: '#1e88e5',
                        color: 'white',
                        border: 'none',
                        borderRadius: '4px',
                        cursor: 'pointer',
                    }}
                >
                    Compartir
                </button>
                <button
                    onClick={onDelete}
                    style={{
                        padding: '8px',
                        backgroundColor: '#f44336',
                        color: 'white',
                        border: 'none',
                        borderRadius: '4px',
                        cursor: 'pointer',
                    }}
                >
                    Eliminar
                </button>
            </div>
        </div>
    );
};

const List = ({ tipoLista }) => {
    const handleAddItem = (newItem) => {
        // Lógica para manejar la adición de elementos a la lista
        console.log(`Nuevo elemento agregado: ${newItem}`);
    };

    const handleApprove = () => {
        alert('Contenido aprobado');
    };

    const handleReject = () => {
        alert('Contenido rechazado');
    };

    const handleShare = () => {
        alert('Compartiendo logro...');
    };

    const handleDelete = () => {
        alert('Eliminando logro...');
    };

    let listaComponente;

    switch (tipoLista) {
        case 'input':
            listaComponente = <ListInput onAddItem={handleAddItem} />;
            break;
        case 'item':
            listaComponente = (
                <ListItem
                    items={[
                        { text: 'Elemento 1', icon: '📌' },
                        { text: 'Elemento 2', button: { label: 'Eliminar', onClick: () => alert('Eliminar') } },
                        { text: 'Elemento 3', icon: '🔔', button: { label: 'Editar', onClick: () => alert('Editar') } },
                    ]}
                />
            );
            break;
        case 'achievement':
            listaComponente = (
                <AchievementList
                    achievement={{ icon: '🏆', text: 'Logro desbloqueado: ¡Ganador del torneo!' }}
                    onShare={handleShare}
                    onDelete={handleDelete}
                />
            );
            break;
        default:
            listaComponente = null;
    }

    return (
        <div>
            <h2>Lista Dinámica</h2>
            {listaComponente}
        </div>
    );
};

export default List;
