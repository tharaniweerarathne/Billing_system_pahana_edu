<%--
  Created by IntelliJ IDEA.
  User: Sasindi Weerarathne
  Date: 8/12/2025
  Time: 8:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Help Section</title>
    <link rel="stylesheet" href="css/help_section.css">
    <!-- Using Remix Icon for UI icons only -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/remixicon/3.5.0/remixicon.css" rel="stylesheet">

</head>
<body>
<div class="container">
    <div class="header-section">
        <div class="logo-container">
            <div class="logo">
                <img src="images/logo1.png" alt="Pahana Edu Logo" class="logo">
            </div>
        </div>
        <h1 class="main-title">Pahana Edu Bookshop</h1>
        <p class="subtitle">Complete Digital Solution for Modern Book Management</p>

        <div class="nav-buttons">
            <button class="nav-btn back-btn hidden" id="backBtn" onclick="showMainContent()">
                <i class="ri-arrow-left-line"></i>
                Back to Main
            </button>
            <button class="nav-btn" onclick="showHelpSection()">
                <i class="ri-question-line"></i>
                Help Guide
            </button>
        </div>
    </div>

    <div id="mainContent" class="content-section">
        <h2 class="section-title">System Features & Capabilities</h2>

        <div class="features-container">
            <div class="feature-item">
                <div class="feature-header">
                    <div class="feature-icon">
                        <i class="ri-shield-user-line"></i>
                    </div>
                    <div class="feature-title">User Authentication</div>
                </div>
                <div class="feature-description">
                    Secure login system with username and password protection. Multi-level access control for different user roles including administrators and staff members.
                </div>
                <div class="feature-highlights">
                    <span class="highlight-tag">Role-Based</span>
                </div>
            </div>

            <div class="feature-item">
                <div class="feature-header">
                    <div class="feature-icon">
                        <i class="ri-team-line"></i>
                    </div>
                    <div class="feature-title">Customer Management</div>
                </div>
                <div class="feature-description">
                    Complete customer registration system with unique account numbers, personal details, contact information.
                </div>
                <div class="feature-highlights">
                    <span class="highlight-tag">Account Numbers</span>
                    <span class="highlight-tag">Profile Management</span>
                    <span class="highlight-tag">Contact Details</span>
                </div>
            </div>

            <div class="feature-item">
                <div class="feature-header">
                    <div class="feature-icon">
                        <i class="ri-edit-box-line"></i>
                    </div>
                    <div class="feature-title">Edit Customer Information</div>
                </div>
                <div class="feature-description">
                    Modify and update customer details including name, address, telephone numbers, and account preferences.
                </div>
                <div class="feature-highlights">
                    <span class="highlight-tag">Real-time Updates</span>
                </div>
            </div>

            <div class="feature-item">
                <div class="feature-header">
                    <div class="feature-icon">
                        <i class="ri-box-3-line"></i>
                    </div>
                    <div class="feature-title">Item Management</div>
                </div>
                <div class="feature-description">
                    Comprehensive book and item catalog with add, update, and delete operations. Inventory control.
                </div>
                <div class="feature-highlights">
                    <span class="highlight-tag">Inventory Control</span>
                </div>
            </div>

            <div class="feature-item">
                <div class="feature-header">
                    <div class="feature-icon">
                        <i class="ri-dashboard-3-line"></i>
                    </div>
                    <div class="feature-title">Account Display</div>
                </div>
                <div class="feature-description">
                    Retrieve and display detailed customer account information including purchase history, outstanding balances, and contact details in organized format.
                </div>
                <div class="feature-highlights">
                    <span class="highlight-tag">Quick Search</span>
                    <span class="highlight-tag">Detailed View</span>
                </div>
            </div>

            <div class="feature-item">
                <div class="feature-header">
                    <div class="feature-icon">
                        <i class="ri-file-list-3-line"></i>
                    </div>
                    <div class="feature-title">Billing System</div>
                </div>
                <div class="feature-description">
                    Calculate and print professional bills based on items purchased and quantity consumed. Automatic tax calculations and discount applications.
                </div>
                <div class="feature-highlights">
                    <span class="highlight-tag">Auto Calculate</span>
                    <span class="highlight-tag">Print Ready</span>
                    <span class="highlight-tag">Discounts Included</span>
                </div>
            </div>

            <div class="feature-item quick-start">
                <div class="feature-header">
                    <div class="feature-icon">
                        <i class="ri-rocket-2-line"></i>
                    </div>
                    <div class="feature-title">Quick Access</div>
                </div>
                <div class="feature-description">
                    Click the "Help Guide" button above for detailed system usage instructions and troubleshooting tips for new users.
                </div>
            </div>
        </div>
    </div>

    <div id="helpContent" class="content-section hidden">
        <div class="help-section">
            <div class="help-title">
                <i class="ri-book-2-line"></i>
                System Help Guide
            </div>

            <div class="help-categories">
                <div class="help-category">
                    <h3>
                        <i class="ri-shield-user-line"></i>
                        1. User Authentication
                    </h3>
                    <div class="help-steps">
                        <ol>
                            <li>Enter your assigned username in the login field</li>
                            <li>Type your secure password</li>
                            <li>Click "Login" button to access the system</li>
                            <li>System will verify credentials and grant access</li>

                        </ol>
                    </div>
                </div>

                <div class="help-category">
                    <h3>
                        <i class="ri-user-add-line"></i>
                        2. Add New Customer Accounts
                    </h3>
                    <div class="help-steps">
                        <ol>
                            <li>Navigate to "Customer Management" section</li>
                            <li>Click "Add New Customer" button</li>
                            <li>System will auto-generate unique account number</li>
                            <li>Fill in customer name, address, and telephone</li>
                            <li>Add any additional contact information</li>
                            <li>Save the record to complete registration</li>
                        </ol>
                    </div>
                </div>

                <div class="help-category">
                    <h3>
                        <i class="ri-edit-2-line"></i>
                        3. Edit Customer Information
                    </h3>
                    <div class="help-steps">
                        <ol>
                            <li>Add a new customer by filling in all required fields</li>
                            <li>Search for customer using account number or name</li>
                            <li>Select the customer record from results</li>
                            <li>Click "Edit" button to modify details</li>
                            <li>Update any required information fields</li>
                            <li>Click "Delete" button to remove a customer record</li>
                            <li>Verify all changes before saving</li>
                        </ol>
                    </div>
                </div>

                <div class="help-category">
                    <h3>
                        <i class="ri-archive-line"></i>
                        4. Manage Item Information
                    </h3>
                    <div class="help-steps">
                        <ol>
                            <li>Go to "Item Management" module</li>
                            <li><strong>For Both Admin and Staff:</strong> Search for items by Item Id , name , category</li>
                            <li><strong>For Admin:</strong>
                                <ul>
                                    <li>To Add: Click "Add New Item" and fill in details</li>
                                    <li>To Update: Search item, select, and modify</li>
                                    <li>To Delete: Select item and confirm deletion</li>
                                </ul>
                            </li>
                            <li><strong>For Staff:</strong> View item list and details only</li>
                            <li>Include items by Item Id , name , category</li>
                        </ol>
                    </div>
                </div>


                <div class="help-category">
                    <h3>
                        <i class="ri-file-list-3-line"></i>
                        5. Display Account Details
                    </h3>
                    <div class="help-steps">
                        <ol>
                            <li>Use search function with account number</li>
                            <li>Or search by customer name</li>
                            <li>System displays complete profile information</li>
                            <li>View purchase history and outstanding balances</li>
                            <li>Export data to PDF or print if needed</li>
                            <li>Use filters to customize displayed information</li>
                        </ol>
                    </div>
                </div>

                <div class="help-category">
                    <h3>
                        <i class="ri-calculator-line"></i>
                        6. Calculate and Print Bills
                    </h3>
                    <div class="help-steps">
                        <ol>
                            <li>Select customer account for billing by searching</li>
                            <li>Add items to the bill bysearching</li>
                            <li>Enter quantities for each item</li>
                            <li>System automatically calculates totals</li>
                            <li>Apply discounts if applicable</li>
                            <li>Generate and print professional invoice</li>
                        </ol>
                    </div>
                </div>

                <div class="help-category">
                    <h3>
                        <i class="ri-user-settings-line"></i>
                        7. Staff Management (Admin Only)
                    </h3>
                    <div class="help-steps">
                        <ol>
                            <li>Navigate to "Staff Management" section in the admin dashboard</li>
                            <li>To Add: Click "Add New Staff", fill in personal details and assign role</li>
                            <li>To Edit: Search staff by name or ID, update information, and save changes</li>
                            <li>To Delete: Select staff record and confirm deletion</li>
                            <li>Assign or modify access permissions as required</li>
                            <li>Save all changes to update staff records in the system</li>
                        </ol>
                    </div>
                </div>
            </div>

            <div class="help-tips">
                <h3>
                    <i class="ri-lightbulb-line"></i>
                    Important Tips & Best Practices
                </h3>
                <ul>
                    <li><strong>Security:</strong> Always log out after completing your session</li>
                    <li><strong>Data Entry:</strong> Double-check all information before saving</li>
                    <li><strong>Account Numbers:</strong> System auto-generates unique IDs - don't modify manually</li>
                    <li><strong>Performance:</strong> Use search filters to improve system response time</li>
                    <li><strong>Support:</strong> Contact system administrator for technical issues</li>
                    <li><strong>Updates:</strong> Keep customer contact information current for better service</li>
                </ul>
            </div>
        </div>
    </div>

    <div class="footer-section">
        <p class="footer-text">Pahana Edu Book Shop</p>
        <div class="contact-info">
            <div class="contact-item">
                <i class="ri-mail-line"></i>
                <span>support@pahanaedu.com</span>
            </div>
            <div class="contact-item">
                <i class="ri-phone-line"></i>
                <span>+011 478 6900/span></span>
            </div>
            <div class="contact-item">
                <i class="ri-global-line"></i>
                <span>www.pahanaedu.com</span>
            </div>
        </div>
    </div>
</div>

<script>
    function showHelpSection() {
        document.getElementById('mainContent').classList.add('hidden');
        document.getElementById('helpContent').classList.remove('hidden');
        document.getElementById('backBtn').classList.remove('hidden');
    }

    function showMainContent() {
        document.getElementById('helpContent').classList.add('hidden');
        document.getElementById('mainContent').classList.remove('hidden');
        document.getElementById('backBtn').classList.add('hidden');
    }


    document.addEventListener('DOMContentLoaded', function() {
        const navButtons = document.querySelectorAll('.nav-btn');
        navButtons.forEach(button => {
            button.addEventListener('click', function() {
                window.scrollTo({
                    top: 0,
                    behavior: 'smooth'
                });
            });
        });


        const observer = new IntersectionObserver((entries) => {
            entries.forEach((entry) => {
                if (entry.isIntersecting) {
                    entry.target.style.animationPlayState = 'running';
                }
            });
        }, { threshold: 0.1 });

        document.querySelectorAll('.feature-item').forEach((item) => {
            item.style.animationPlayState = 'paused';
            observer.observe(item);
        });
    });


    document.querySelectorAll('.feature-item').forEach(item => {
        item.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-12px) rotateX(5deg)';
        });

        item.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0) rotateX(0)';
        });
    });
</script>

</body>
</html>
