FROM php:8.0-apache

# Install necessary system dependencies
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    zip \
    && rm -rf /var/lib/apt/lists/*

# Install and enable pdo_mysql
RUN docker-php-ext-install pdo_mysql && docker-php-ext-enable pdo_mysql

# Enable Apache mod_rewrite
RUN a2enmod rewrite

# Copy custom Apache configuration
COPY c2_server.conf /etc/apache2/sites-available/c2_server.conf

# Enable the custom site and disable the default one
RUN a2ensite c2_server.conf && a2dissite 000-default.conf

# Copy Composer binary
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

# Set working directory
WORKDIR /var/www/html/

# Copy application files
COPY . /var/www/html/

# Set permissions
RUN chown -R www-data:www-data /var/www/html && chmod -R 755 /var/www/html

# Install Composer dependencies
RUN composer install --no-dev --optimize-autoloader

# Expose port 80 and start Apache
EXPOSE 80
CMD ["apache2-foreground"]
